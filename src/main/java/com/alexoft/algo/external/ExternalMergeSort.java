package com.alexoft.algo.external;

import java.io.File;

/**
 * Common interface for merge sort implementations
 */
public interface ExternalMergeSort {

    /**
     * Sorts a file of integers
     * @param file file with integers
     */
    void sort(File file);

    /**
     * Gets an order of sorting
     * @return true for ascending, false for descending
     */
    boolean isAscending();
}
