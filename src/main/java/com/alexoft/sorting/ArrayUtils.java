package com.alexoft.sorting;

/**
 * Helper methods for arrays
 */
public abstract class ArrayUtils {

    /**
     * Copies an array using fast native function
     * @param A source array
     * @param iBegin begin index (inclusive)
     * @param iEnd end index (exclusive)
     * @param B destination array
     */
    public static void CopyArray(int[] A, int iBegin, int iEnd, int[] B) {
        if (iEnd - iBegin >= 0) System.arraycopy(A, iBegin, B, iBegin, iEnd - iBegin);
    }

    /**
     * Copies an array using fast native function
     * @param A source array
     * @param B destination array
     * @param n array length
     */
    public static void CopyArray(int[] A, int[] B, int n) {
        if (n >= 0) System.arraycopy(A, 0, B, 0, n);
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
    public static void TopDownMerge(int[] A, int iBegin, int iMiddle, int iEnd, int[] B) {
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
