package com.alexoft.sorting;

import com.alexoft.algo.external.ExternalMergeSort;

import java.io.File;

/**
 * Interface that combines all the processing of sorting array,
 * logging algorithm statistics and saving output to file.
 */
public interface Sorting {

    /**
     * Adds a MergeSort implementation for later processing.
     * @param mergeSort MergeSort implementation object
     */
    void add(ExternalMergeSort mergeSort);

    /**
     * Iteratively applies sorting algorithms to an input array.
     * @param numbers integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void process(File numbers, boolean asc);

    /**
     * Sorts data array using selected sorting method.
     * @param sorter MergeSort implementation object
     * @param data integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void apply(ExternalMergeSort sorter, File data, boolean asc);
}
