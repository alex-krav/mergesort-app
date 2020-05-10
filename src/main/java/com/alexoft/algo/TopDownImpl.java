package com.alexoft.algo;

import com.alexoft.log.Logger;

/**
 * Algorithm that recursively splits the list (called runs in this example) into sublists
 * until sublist size is 1, then merges those sublists to produce a sorted list.
 */
public class TopDownImpl implements MergeSort {
    private AlgoStats algoStats;
    private Logger logger;
    private boolean asc = true;

    @Override
    public void sort(int[] A) {
        if (A == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        algoStats = new AlgoStats("Merge sort");
        log("Merge sort is starting...");
        int n = A.length;
        if (n == 0)
            return;
        int[] B = new int[n]; // array B[] is a work array
        CopyArray(A, B); // one time copy of A[] to B[]
        TopDownSplitMerge(B, 0, n, A); // sort data from B[] into A[]
        algoStats.addCopies();
        algoStats.setArraySize(n);
        log("Merge sort output", A);
    }

    /**
     * Sort the given run of array A[] using array B[] as a source.
     * iBegin is inclusive; iEnd is exclusive (A[iEnd] is not in the set).
     * @param B source array
     * @param iBegin begin index (inclusive)
     * @param iEnd end index (exclusive)
     * @param A result array
     */
    private void TopDownSplitMerge(int[] B, int iBegin, int iEnd, int[] A) {
        if(iEnd - iBegin < 2) // if run size == 1, consider it sorted
            return;
        // split the run longer than 1 item into halves
        int iMiddle = (iEnd + iBegin) / 2; // iMiddle = mid point
        // recursively sort both runs from array A[] into B[]
        TopDownSplitMerge(A, iBegin,  iMiddle, B); // sort the left  run
        TopDownSplitMerge(A, iMiddle,    iEnd, B); // sort the right run
        // merge the resulting runs from array B[] into A[]
        TopDownMerge(B, iBegin, iMiddle, iEnd, A);
        algoStats.addSplits();
        algoStats.addMerges();
        log("Merge sort interim result", A);
    }

    @Override
    public AlgoStats getStats() {
        return algoStats;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        if (null != logger)
            logger.print(message);
    }

    @Override
    public void log(String message, int[] numbers) {
        if (null != logger)
            logger.print(message, numbers);
    }

    @Override
    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    @Override
    public boolean getAsc() {
        return asc;
    }
}
