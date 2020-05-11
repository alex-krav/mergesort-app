package com.alexoft.ui;

import com.alexoft.log.Logger;
import com.alexoft.parser.Parser;
import com.alexoft.random.IntGenerator;
import com.alexoft.sorting.Sorting;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller class, that ties together app UI and sorting algorithms
 */
public class Controller {
    private Model model;
    private View view;
    private Parser parser;
    private IntGenerator intGenerator;
    private Sorting sorting;
    private ExecutorService executor;
    private Logger fileLogger;

    public Controller(Model m, View v) {
        model = m;
        view = v;
    }

    /**
     * Initializes UI events for control buttons
     */
    public void initController() {
        view.getSelectButton().addActionListener(event -> selectFile());
        view.getAscRadioButton().addActionListener(event -> setAscOrder());
        view.getDescRadioButton().addActionListener(event -> setDescOrder());
        view.getSortButton().addActionListener(event -> sort());
    }

    /**
     * Opens a file chooser window to select input file
     */
    private void selectFile() {
        model.setActiveTab(view.getActiveTab());
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(view.getFileTab()) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            view.setFileName(trim(file.getName()));
            model.setInputFile(file);
        }
    }

    /**
     * Toggles sorting order to ascending direction
     */
    private void setAscOrder() {
        if (model.isAsc()) return;
        model.setAsc(true);
    }

    /**
     * Toggles sorting order to descending direction
     */
    private void setDescOrder() {
        if (!model.isAsc()) return;
        model.setAsc(false);
    }

    /**
     * Sorts selected input array
     */
    private void sort() {
        enableMenu(false);
        model.setActiveTab(view.getActiveTab());

        int[] inputArray = getInputArray();
        if (null != inputArray) {
            view.openLogPane();
            executor = Executors.newSingleThreadExecutor();
            Runnable task = new SortingTask(inputArray, model.isAsc());
            executor.execute(task);
        } else {
            enableMenu(true);
        }
    }

    /**
     * Enables or disables View menu elements depending on param
     * @param enabled param (true - enable, false - disable)
     */
    private void enableMenu(boolean enabled) {
        view.enableTabs(enabled);
        view.getSortButton().setEnabled(enabled);
        view.enableOrder(enabled);
    }

    /**
     * Selects input data and validates it.
     * @return input array
     */
    private int[] getInputArray() {
        int[] array = null;
        if (model.getActiveTab() == Model.Tab.TEXT) {
            // if current tab is TEXT, read input array from text field
            model.setInputText(view.getInputText());
            String inputText = model.getInputText();
            if (inputText.isEmpty())
                JOptionPane.showMessageDialog(view.getFrame(), "Empty input text!", "Error", JOptionPane.ERROR_MESSAGE);
            else
                try {
                    array = parser.readString(model.getInputText());
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Wrong input text!", "Error", JOptionPane.ERROR_MESSAGE);
                }
        } else if (model.getActiveTab() == Model.Tab.FILE) {
            // if current tab is FILE, read input file
            File inputFile = model.getInputFile();
            if (null == inputFile)
                JOptionPane.showMessageDialog(view.getFrame(), "File not selected!", "Error", JOptionPane.ERROR_MESSAGE);
            else
                try {
                    array = parser.readFile(model.getInputFile());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Wrong input file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
        } else {
            // if current tab is GENERATE, create array of random integers
            try {
                model.setArraySize(Integer.valueOf(view.getArraySize()));
                // minimum array size is 2
                if (model.getArraySize() < 2) throw new IllegalArgumentException();
                // either both min/max empty or both have values
                if (view.getMinValue().isEmpty() && view.getMaxValue().isEmpty()) {
                    model.setMinValue(null);
                    model.setMaxValue(null);
                } else {
                    model.setMinValue(Integer.valueOf(view.getMinValue()));
                    model.setMaxValue(Integer.valueOf(view.getMaxValue()));
                    // min value must be less than max value
                    if (model.getMinValue() > model.getMaxValue()) throw new IllegalArgumentException();
                }

                array = intGenerator.generate(model.getArraySize(), model.getMinValue(), model.getMaxValue());
                fileLogger.print("input", array);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view.getFrame(), "Wrong numbers!\nSet only array size (min 2) or\nsize with min and max values\n(min value < max value).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return array;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setIntGenerator(IntGenerator intGenerator) {
        this.intGenerator = intGenerator;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    /**
     * Trims input file name to 40 characters before displaying it on view,
     * so that it fits on panel
     * @param name file name
     * @return trimmed version of file name
     */
    private static String trim(String name) {
        int extId = name.lastIndexOf('.');
        String ext = (extId > -1) ? name.substring(extId) : "";
        if (name.length() > 40)
            return name.substring(0, 40) + "... " + ext;
        else
            return name;
    }

    public void setFileLogger(Logger fileLogger) {
        this.fileLogger = fileLogger;
    }

    /**
     * Class for running sorting in separate thread to not block UI
     */
    class SortingTask implements Runnable {
        private int[] data;
        private boolean asc;

        public SortingTask(int[] data, boolean asc) {
            this.data = data;
            this.asc = asc;
        }

        @Override
        public void run() {
            sorting.process(data, asc);
            enableMenu(true);
        }
    }
}
