package com.alexoft.algo;

import java.util.*;

public class PolyphaseMergeSort extends MergeSortBase{
    private static final int partitions = 10;
    private List<List<Integer>> runs;

    @Override
    public void sort(int[] A) {
        long startTime = System.nanoTime();
        if (A == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = A.length;
        if (n == 0)
            return;
        log(String.format("Polyphase merge sort is starting %s...", getAscString()));
        algoStats = new AlgoStats("Polyphase merge sort");
        initInterimResultCounters(n);
        polyphaseSplit(A);
        algoStats.setArraySize(n);
        log("Polyphase merge sort output", A);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    private void polyphaseSplit(int[] source) {
        if (source.length <= partitions) {
            binarySort(source);
            return;
        }
        createInitialRuns(source);
        mergeArrays(source);
    }

    private void createInitialRuns(int[] source) {
        int size = source.length;
        int i, current = 0;
        int[] temp;

        runs = new ArrayList<>(partitions);
        int partitionSize = Math.max(size / partitions, 1);
        int endPartitionSize = size - (partitionSize * (Math.min(size, partitions) - 1));

        for (i = 0; i < partitions - 1; ++i) {
            temp = new int[partitionSize];
            System.arraycopy(source, current, temp,0, partitionSize);
            binarySort(temp);
            runs.add(new ArrayList<>(partitionSize)); for(int j : temp) runs.get(i).add(j);
            current+= partitionSize;
        }

        temp = new int[endPartitionSize];
        System.arraycopy(source, current, temp,0, endPartitionSize);
        binarySort(temp);
        runs.add(new ArrayList<>(endPartitionSize)); for(int j : temp) runs.get(i).add(j);
    }

    private void mergeArrays(int[] source) {
        int id = 0;
        PriorityQueue<MinHeapNode> pq = new PriorityQueue<>(10,
                isAscending() ? new AscComparator() : new DescComparator());

        List<Iterator<Integer>> iter = new ArrayList<>(partitions);
        for(int i = 0; i < partitions; ++i) {
            iter.add(runs.get(i).iterator());
        }

        int i;
        /*MinHeapNode[] harr = new MinHeapNode[partitions];
        for(i = 0; i < partitions; ++i) {
            if(iter.get(i).hasNext()) {
                MinHeapNode node = new MinHeapNode();
                node.element = iter.get(i).next();
                harr[i] = node;
            } else {
                break;
            }
            harr[i].i = i;
            pq.add(harr[i]);
        }*/
        for(i = 0; i < partitions; ++i) {
            MinHeapNode node = new MinHeapNode();
            node.element = iter.get(i).next();
            node.i = i;
            pq.add(node);
        }

        int count = 0;
        while (count != i) {
            MinHeapNode root = pq.remove();
            source[id++] = root.element;

            if (iter.get(root.i).hasNext()) {
                root.element = iter.get(root.i).next();
                pq.add(root);
            } else {
                count++;
            }
            logInterim("Polyphase merge sort interim result", source);
        }
        algoStats.addMerges();
    }

    private void binarySort(int[] A) {
        int n = A.length;
        int[] B = new int[n];
        CopyArray(A, B); // one time copy of A[] to B[]
        algoStats.addCopies();
        binarySplit(B, 0, n, A); // sort data from B[] into A[]
    }

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
    }

    private static class MinHeapNode {
        public int element;
        public Integer i;
    }
    private static class AscComparator implements Comparator<MinHeapNode> {
        @Override
        public int compare(MinHeapNode left, MinHeapNode right) {
            return left.element - right.element;
        }
    }
    private static class DescComparator implements Comparator<MinHeapNode> {
        @Override
        public int compare(MinHeapNode left, MinHeapNode right) {
            return right.element - left.element;
        }
    }
}

