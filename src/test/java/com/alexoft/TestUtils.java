package com.alexoft;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Helper methods for test classes
 */
public abstract class TestUtils {

    /**
     * Prints first n elements of input array
     * @param numbers array of integers
     * @param n number of elements to print
     * @return string of first n array items
     */
    public static String printFirstN(int[] numbers, int n) {
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0; i < n; ++i) {
            builder.append(numbers[i]);
            if (i < (n-1)) builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Gets file object for filename
     * @param filename file name
     * @return file object
     * @throws URISyntaxException if location of codesource
     * cannot be converted to URI
     */
    public static File getFile(String filename) throws URISyntaxException {
        return Paths.get(
                TestUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()
        ).resolve(
                Paths.get(filename)
        ).toFile();
    }
}
