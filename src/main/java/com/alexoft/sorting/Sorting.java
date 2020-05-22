package com.alexoft.sorting;

import com.alexoft.algo.external.ExternalMergeSort;

import java.io.File;

/**
 * Interface that combines all the processing of sorting array,
 * logging algorithm statistics and saving output to file.
 */
public interface Sorting {

    /**
     * Adds an ExternalMergeSort implementation for later processing.
     * @param mergeSort ExternalMergeSort implementation object
     */
    void add(ExternalMergeSort mergeSort);

    /**
     * Iteratively applies sorting algorithms to an input file.
     * @param numbers file with integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void process(File numbers, boolean asc);

    /**
     * Sorts data array using selected sorting method.
     * @param sorter ExternalMergeSort implementation object
     * @param data file with integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    void apply(ExternalMergeSort sorter, File data, boolean asc);
}
