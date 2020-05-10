package com.alexoft.ui;

import com.alexoft.parser.Parser;
import com.alexoft.random.IntGenerator;
import com.alexoft.sorting.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.alexoft.ui.Validator.trim;

public class Controller {
    private Model model;
    private View view;
    private Parser parser;
    private IntGenerator intGenerator;
    private Sorting sorting;
    private ExecutorService executor;

    public Controller(Model m, View v) {
        model = m;
        view = v;
    }

    public void initController() {
        view.getSelectButton().addActionListener(event -> selectFile());
        view.getAscRadioButton().addActionListener(event -> setAscOrder());
        view.getDescRadioButton().addActionListener(event -> setDescOrder());
        view.getSortButton().addActionListener(event -> sort());
    }

    private void selectFile() {
        model.setActiveTab(view.getActiveTab());
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(view.getFileTab()) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            view.setFileName(trim(file.getName()));
            model.setInputFile(file);
        }
        model.debug();
    }

    private void setAscOrder() {
        if (model.isAsc()) return;
        model.setAsc(true);
        model.debug();
    }

    private void setDescOrder() {
        if (!model.isAsc()) return;
        model.setAsc(false);
        model.debug();
    }

    private void sort() {
        enableMenu(false);
        model.setActiveTab(view.getActiveTab());

        int[] inputArray = getInputArray();
        if (null != inputArray) {
            view.openLogPane();
            executor = Executors.newSingleThreadExecutor();
            Runnable task = new SortingTask(inputArray);
            executor.execute(task);
        } else {
            enableMenu(true);
        }
    }

    private void enableMenu(boolean enabled) {
        view.enableTabs(enabled);
        view.getSortButton().setEnabled(enabled);
        view.enableOrder(enabled);
    }

    private int[] getInputArray() {
        int[] array = null;
        if (model.getActiveTab() == Model.Tab.TEXT) {
            model.setInputText(view.getInputText());
            model.debug();
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
            model.debug();
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
            try {
                model.setArraySize(Integer.valueOf(view.getArraySize()));
                if (view.getMinValue().isEmpty() && view.getMaxValue().isEmpty()) {
                    model.setMinValue(null);
                    model.setMaxValue(null);
                } else {
                    model.setMinValue(Integer.valueOf(view.getMinValue()));
                    model.setMaxValue(Integer.valueOf(view.getMaxValue()));
                }
                model.debug();

                array = intGenerator.generate(model.getArraySize(), model.getMinValue(), model.getMaxValue());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view.getFrame(), "Wrong numbers!\nSet only array size or\nsize with min and max values.", "Error", JOptionPane.ERROR_MESSAGE);
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

    class SortingTask implements Runnable {
        private int[] data;

        public SortingTask(int[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                sorting.process(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            enableMenu(true);
        }
    }
}
