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
}
