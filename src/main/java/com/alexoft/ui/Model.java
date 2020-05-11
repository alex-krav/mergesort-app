package com.alexoft.ui;

import java.io.File;

/**
 * Model class for holding state of input data and parameters
 */
public class Model {
    public static final String EMPTY = "";

    private Enum<Tab> activeTab = Tab.TEXT; // currently selected input tab
    private String inputText = EMPTY; // input text with array
    private File inputFile = null; // input file with array
    private Integer arraySize = null; // size of random array
    private Integer minValue = null; // minimal value of random array (inclusive)
    private Integer maxValue = null; // maximum value of random array (inclusive)
    private boolean asc = true; // true for ascending order, false for descending

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

    /**
     * Enum class for input tabs
     */
    public enum Tab {
        TEXT,
        FILE,
        RANDOM
    }
}

