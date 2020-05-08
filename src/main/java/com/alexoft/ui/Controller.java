package com.alexoft.ui;

import javax.swing.*;
import java.io.File;

import static com.alexoft.ui.Validator.trim;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model m, View v) {
        model = m;
        view = v;
    }

    public void initController() {
        view.getSelectButton().addActionListener(event -> selectFile());
        view.getAscRadioButton().addActionListener(event -> setAscOrder());
        view.getDescRadioButton().addActionListener(event -> setDescOrder());
        view.getSortButton().addActionListener(event -> sort());
        view.getCancelButton().addActionListener(event -> cancel());
        view.getSaveButton().addActionListener(event -> save());
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
        model.debug();
        enableMenu(false);
        view.openLogPane();

        // getInputArray(tab-> readString, readFile, generate)
        // sort -> thread
        // getOutputArray
        // enableMenu(true);
    }

    private void cancel() {
        // interrupt sorting thread
        // menu will be enabled in sort() method
    }

    private void save() {
        // filechooser -> filename -> write(filename, outputArray) -> thread
    }

    public void enableMenu(boolean enabled) {
        view.enableTabs(enabled);
        view.getSortButton().setEnabled(enabled);
        view.getCancelButton().setEnabled(!enabled);
        view.getSaveButton().setEnabled(enabled);
        view.enableOrder(enabled);
    }
}
