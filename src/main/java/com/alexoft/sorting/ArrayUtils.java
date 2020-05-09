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
        int length = a.length;
        if (length == 0)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append("[ ");
        for (int i = 0; i < 5; ++i) {
            b.append(a[i]);
            b.append(" ");
        }
        b.append("... ");
        for (int i = length-5; i < length; ++i) {
            b.append(a[i]);
            b.append(" ");
        }
        return b.append(']').toString();
    }
}
