package com.alexoft.sorting;

import com.alexoft.algo.MergeSort;

import java.io.IOException;

/**
 * Class that combines all the processing of sorting array,
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
     * Method creates a copy of array before invoking new sort.
     * @param numbers integer array
     * @throws IOException if output file can't be created
     */
    void process(int[] numbers, boolean asc) throws IOException;

    /**
     * Sorts data array using selected sorting method. Meanwhile, interim
     * results and output array are logged. Output array is saved to file.
     * Method changes input array.
     * @param sorter MergeSort implementation object
     * @param data integer array
     * @throws IOException if output file can't be created
     */
    void apply(MergeSort sorter, int[] data, boolean asc) throws IOException;
}
