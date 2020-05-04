package com.alexoft.sorting;

import java.util.LinkedList;
import java.util.List;

import static com.alexoft.sorting.ArrayUtils.CopyArray;
import static com.alexoft.sorting.ArrayUtils.TopDownMerge;

/**
 * First, the input is searched for any naturally occurring runs (sorted sequences).
 * Then each subsequent pair of runs is merged until all the pairs are merged.
 */
public class NaturalMergeSortImpl implements MergeSort {

    @Override
    public void sort(int[] A) {
        int n = A.length;
        int[] B = new int[n]; // array B[] is a work array
        NaturalMergeSort(A, B, n);
    }

    /**
     * Sort array using naturally occuring runs.
     * @param A source array
     * @param B work array
     * @param n source array length
     */
    private void NaturalMergeSort(int[] A, int[] B, int n) {
        List<Integer> runs = getSortedRunsIndices(A, n);

        while(runs.size() > 2) { // merge all runs, but last two
            for (int i = 0; i < runs.size(); ++i) {
                if (runs.get(i) != n)
                    // recursively merge both runs from array A[] into B[],
                    // get next indices or last index of list
                    TopDownMerge(A, runs.get(i),
                        runs.get(Math.min((i+1),runs.size()-1)),
                        runs.get(Math.min((i+2),runs.size()-1)), B);
                // remove middle index of two subsequent runs
                if (i < runs.size()-1) runs.remove(i+1);
                if (runs.size() == 2) break;
            }
            CopyArray(B, A, n);
        }
        // merge last two runs from array A[] into B[]
        TopDownMerge(A, runs.get(0), runs.get(1), n, B);
        CopyArray(B, A, n); // finally copy of B[] to A[]
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
            if (endId >= n || A[endId] < A[i]) queue.add(endId);
        }
        return queue;
    }
}
