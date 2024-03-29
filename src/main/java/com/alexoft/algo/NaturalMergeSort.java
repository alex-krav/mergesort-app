package com.alexoft.algo;

import java.util.LinkedList;
import java.util.List;

/**
 * First, the input is searched for any naturally occurring runs (sorted sequences).
 * Then each subsequent pair of runs is merged until all the pairs are merged.
 */
public class NaturalMergeSort extends MergeSortBase {

    @Override
    public void sort(int[] A) {
        long startTime = System.nanoTime();
        if (A == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = A.length;
        if (n == 0)
            return;
        log(String.format("Natural merge sort is starting %s...", getAscString()));
        algoStats = new AlgoStats("Natural merge sort");
        initInterimResultCounters(n);
        int[] B = new int[n]; // array B[] is a work array
        naturalSplit(A, B, n);
        algoStats.setArraySize(n);
        log("Natural merge sort output", A);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Sort array using naturally occurring runs.
     * @param A source array
     * @param B work array
     * @param n source array length
     */
    private void naturalSplit(int[] A, int[] B, int n) {
        List<Integer> runs = getSortedRunsIndices(A, n);

        while(runs.size() > 2) { // merge all runs, but last two
            for (int i = 0; i < runs.size(); ++i) {
                if (runs.get(i) != n) {
                    // recursively merge both runs from array A[] into B[],
                    // get next indices or last index of list
                    binaryMerge(A, runs.get(i),
                            runs.get(Math.min((i+1),runs.size()-1)),
                            runs.get(Math.min((i+2),runs.size()-1)), B);
                    algoStats.addMerges();
                }
                // remove middle index of two subsequent runs
                if (i < runs.size()-1 && runs.get(i+1) != n)
                    runs.remove(i+1);
                if (runs.size() == 2) break;
            }
            CopyArray(B, A);
            logInterim("Natural merge sort interim result", A);
            algoStats.addCopies();
        }
        // merge last two runs from array A[] into B[]
        binaryMerge(A, runs.get(0), runs.get(1), n, B);
        CopyArray(B, A); // finally copy of B[] to A[]
        algoStats.addSplits();
        algoStats.addMerges();
        algoStats.addCopies();
    }

    /**
     * Gets list of indices of sorted runs. First is '0', last is 'n',
     * all in between are end indices (exclusive) of previous run,
     * at the same time being begin indices (inclusive) of next run.
     * @param A source array
     * @param n array length
     * @return list of starting indices of sorted runs
     */
    private List<Integer> getSortedRunsIndices(int[] A, int n) {
        List<Integer> queue = new LinkedList<>();
        queue.add(0);
        for (int i = 0; i < n; ++i) {
            int endId = i + 1;
            // if next element is less than current, or this is
            // the last element, add end index
            if (endId >= n || (A[endId] < A[i] == isAscending())) queue.add(endId);
        }
        return queue;
    }
}
