package com.alexoft.sorting;

/**
 * Common interface for merge sort implementations
 */
public interface MergeSort {

    /**
     * Sorts an array of integers
     * @param A array of integers
     */
    void sort(int[] A);

    /**
     * Copies an array using fast native function
     * @param A source array
     * @param B destination array
     */
    default void CopyArray(int[] A, int[] B) {
        int length = A.length;
        System.arraycopy(A, 0, B, 0, length);
    }

    /**
     * Left source half is A[ iBegin:iMiddle-1 ].
     * Right source half is A[ iMiddle:iEnd-1 ].
     * Result is B[ iBegin:iEnd-1 ].
     * @param A source array
     * @param iBegin begin index (inclusive)
     * @param iMiddle middle index
     * @param iEnd end index (exclusive)
     * @param B result array
     */
    default void TopDownMerge(int[] A, int iBegin, int iMiddle, int iEnd, int[] B) {
        int i = iBegin, j = iMiddle;
        // While there are elements in the left or right runs...
        for (int k = iBegin; k < iEnd; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < iMiddle && (j >= iEnd || A[i] <= A[j])) {
                B[k] = A[i];
                i = i + 1;
            } else {
                B[k] = A[j];
                j = j + 1;
            }
        }
    }
}
