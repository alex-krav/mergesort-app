package com.alexoft.sorting;

/**
 * Helper methods for arrays
 */
public abstract class ArrayUtils {

    /**
     * Prints array to string in the format
     * [item1 item2 item3] space-separated
     * @param a integer array
     * @return string representation of array
     */
    public static String print(int[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(" ");
        }
    }
}
