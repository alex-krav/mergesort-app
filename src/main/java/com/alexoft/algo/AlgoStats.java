package com.alexoft.algo;

/**
 * Accumulating object for gathering algorithm statistics
 */
public class AlgoStats {

    private String algoName; // algorithm name
    private int arraySize; // array size
    private int splits; // number of array splits
    private int merges; // number of array merges
    private int copies; // number of array copying

    public AlgoStats(String algoName) {
        this.algoName = algoName;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public void addSplits() {
        ++splits;
    }

    public void addMerges() {
        ++merges;
    }

    public void addCopies() {
        ++copies;
    }

    public String getAlgoName() {
        return algoName;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getSplits() {
        return splits;
    }

    public int getMerges() {
        return merges;
    }

    public int getCopies() {
        return copies;
    }
}
