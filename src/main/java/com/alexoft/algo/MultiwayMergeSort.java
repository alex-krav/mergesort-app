package com.alexoft.algo;

import java.util.LinkedList;
import java.util.List;

/**
 *  Multiway merges are a specific type of sequence merge algorithms that specialize in
 *  taking in k sorted lists and merging them into a single sorted list.
 */
public class MultiwayMergeSort extends MergeSortBase {
    // number of parts, source array will be split in
    // 2 by default, making it a BottomUp implementation
    // of normal, binary merge sort
    private int k = 2;

    public MultiwayMergeSort() {}

    public MultiwayMergeSort(int k) {
        this.k = k;
    }

    @Override
    public void sort(int[] A) {
        long startTime = System.nanoTime();
        if (A == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = A.length;
        if (n == 0)
            return;
        log(String.format("Multiway merge sort (k=%d) is starting...", k));
        algoStats = new AlgoStats(String.format("Multiway merge sort (k=%d)", k));
        initInterimResultCounters(n);
        int[] B = new int[n]; // array B[] is a work array
        CopyArray(A, B); // one time copy of A[] into B[]
        KwaySplit(A, B, 0, n-1);
        algoStats.addCopies();
        algoStats.setArraySize(n);
        log(String.format("Multiway merge sort (k=%d) output", k), A);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Setter method for k number
     * @param k number of splits
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * Split array into k parts recursively until one item in each part left.
     * Then merge every subsequent k parts together.
     * @param A source array
     * @param B destination array
     * @param low begin index (inclusive)
     * @param high end index (inclusive)
     */
    private void KwaySplit(int[] A, int[] B, int low, int high) {
        int size = high - low + 1;
        if (size < 2) return;
        List<Integer> runs = getKsplitRunsIndices(low, high);

        for (int i = 0; i < runs.size() - 1; ++i) {
            KwaySplit(A, B, runs.get(i), runs.get(++i));
        }
        KwayMerge(A, B, runs);
        CopyArray(B, A); // finally copy array B into A
        logInterim(String.format("Multiway merge sort (k=%d) interim result", k), A);
        algoStats.addSplits();
        algoStats.addCopies();
    }

    /**
     * Build list of indices of begin and end indices of k split parts. For example,
     * if k=3, than we'll have k*2=6 indices, eg [ begin1, end1, begin2, end2, begin3, end3 ]
     * @param low begin index (inclusive)
     * @param high end index (inclusive)
     * @return list of run indices
     */
    private List<Integer> getKsplitRunsIndices(int low, int high) {
        int size = high - low + 1; // size of current list
        // size of partition must be minimum 1
        int sizePartitions = Math.max(size / this.k, 1);
        // size of end partition may differ than other partitions
        int sizeEndPartition = size - (sizePartitions * (Math.min(size, this.k) - 1));
        List<Integer> runs = new LinkedList<>();

        int id = low;
        int end = Math.min(size, this.k);
        // add indices of all runs but last one
        for(int i = 0; i < end - 1; ++i) {
            runs.add(id); // begin index (inclusive)
            id += sizePartitions;
            runs.add(id-1); // end index (inclusive)
        }
        runs.add(id); // begin index of last run
        id += (sizeEndPartition);
        runs.add(--id); // end index of last run
        return runs;
    }

    /**
     * Merge k lists into one
     * @param A source array
     * @param B destination array
     * @param runs indices of k lists
     */
    private void KwayMerge(int[] A, int[] B, List<Integer> runs) {
        int left = runs.get(0); // begin index of k lists (inclusive)
        int end = runs.get(runs.size()-1)+1; // end index of k lists (exclusive)
        for (int i = left; i < end; ++i) {
            B[i] = getValue(A, runs);
        }
        algoStats.addMerges();
    }

    /**
     * Get min value from k sorted lists
     * @param A source array
     * @param runs list of k lists indices
     * @return min value from k lists
     */
    private int getValue(int[] A, List<Integer> runs) {
        int minId = 0; // variables must be default initialized
        int min = 0;
        boolean first = true;
        // for each of k lists
        for (int i = 0; i < runs.size(); i+=2) {
            int id = runs.get(i); // get begin index of list
            // if no more elements left in list
            if (id > (runs.get(i+1))) continue;
            if (first) { // must get initial values for first iteration
                minId = i; min = A[id]; first = false; continue;
            }
            if (A[id] < min == isAscending()) { // if current value less than minimal value
                min = A[id];
                minId = i;
            }
        }
        // increment begin index of list, so that when it's bigger than end index
        // we know, that this list is over
        runs.set(minId, runs.get(minId)+1);

        return min;
    }
}
