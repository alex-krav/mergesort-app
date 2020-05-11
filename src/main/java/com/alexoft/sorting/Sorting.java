package com.alexoft.sorting;

import com.alexoft.algo.MergeSort;

/**
 * Interface that combines all the processing of sorting array,
 * logging algorithm statistics and saving output to file.
 */
public interface Sorting {

    /**
     * Adds a MergeSort implementation for later processing.
     * @param mergeSort MergeSort implementation object
     */
    void add(MergeSort mergeSort);

    /**
     * Iteratively applies sorting algorithms to an input array.
     * @param numbers integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void process(int[] numbers, boolean asc);

    /**
     * Sorts data array using selected sorting method.
     * @param sorter MergeSort implementation object
     * @param data integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void apply(MergeSort sorter, int[] data, boolean asc);
}
