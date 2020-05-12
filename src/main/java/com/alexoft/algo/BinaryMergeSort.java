package com.alexoft.algo;

/**
 * Algorithm that recursively splits the list (called runs in this example) into sublists
 * until sublist size is 1, then merges those sublists to produce a sorted list.
 */
public class BinaryMergeSort extends MergeSortBase {

    @Override
    public void sort(int[] A) {
        long startTime = System.nanoTime();
        if (A == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = A.length;
        if (n == 0)
            return;
        log(String.format("Binary merge sort is starting %s...", getAscString()));
        algoStats = new AlgoStats("Binary merge sort");
        initInterimResultCounters(n);
        int[] B = new int[n]; // array B[] is a work array
        CopyArray(A, B); // one time copy of A[] to B[]
        binarySplit(B, 0, n, A); // sort data from B[] into A[]
        algoStats.addCopies();
        algoStats.setArraySize(n);
        log("Binary merge sort output", A);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Sort the given run of array A[] using array B[] as a source.
     * iBegin is inclusive; iEnd is exclusive (A[iEnd] is not in the set).
     * @param B source array
     * @param iBegin begin index (inclusive)
     * @param iEnd end index (exclusive)
     * @param A result array
     */
    private void binarySplit(int[] B, int iBegin, int iEnd, int[] A) {
        if(iEnd - iBegin < 2) // if run size == 1, consider it sorted
            return;
        // split the run longer than 1 item into halves
        int iMiddle = (iEnd + iBegin) / 2; // iMiddle = mid point
        // recursively sort both runs from array A[] into B[]
        binarySplit(A, iBegin,  iMiddle, B); // sort the left  run
        binarySplit(A, iMiddle,    iEnd, B); // sort the right run
        // merge the resulting runs from array B[] into A[]
        binaryMerge(B, iBegin, iMiddle, iEnd, A);
        algoStats.addSplits();
        algoStats.addMerges();
        logInterim("Binary merge sort interim result", A);
    }
}
