package com.alexoft.ui;

import java.io.File;

public class Model {
    public static final String EMPTY = "";

    private Enum<Tab> activeTab = Tab.TEXT;
    private String inputText = EMPTY;
    private File inputFile = null;
    private Integer arraySize = null;
    private Integer minValue = null;
    private Integer maxValue = null;
    private boolean asc = true;

    public Model() {}

    public Enum<Tab> getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(Enum<Tab> activeTab) {
        this.activeTab = activeTab;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public Integer getArraySize() {
        return arraySize;
    }

    public void setArraySize(Integer arraySize) {
        this.arraySize = arraySize;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public void debug() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "[tab=" + activeTab + ", text=" + inputText + ", file=" + getFileName()
                + ", size=" + arraySize + ", min=" + minValue + ", max=" + maxValue
                + ", asc=" + asc + "]";
    }

    public String getFileName() {
        return (null != inputFile) ? inputFile.getName() : "null";
    }

    public enum Tab {
        TEXT,
        FILE,
        RANDOM
    }
}

