package com.alexoft;

public abstract class TestUtils {

    private String printFirstN(int[] array, int n) {
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0; i < n; ++i) {
            builder.append(array[i]);
            if (i < (n-1)) builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
