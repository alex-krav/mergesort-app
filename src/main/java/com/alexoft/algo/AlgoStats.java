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
    private long timeNanoSeconds; // time of algorithm work in nanoseconds

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

    public long getTimeNanoSeconds() {
        return timeNanoSeconds;
    }

    public void setTimeNanoSeconds(long timeNanoSeconds) {
        this.timeNanoSeconds = timeNanoSeconds;
    }

    /**
     * Counts complexity of external merge algorithm using formula:
     * 2*N*(splits+merges)
     * @return complexity number
     */
    public long countComplexity() {
        return 2L*getArraySize()*(getMerges()+getSplits());
    }
}
