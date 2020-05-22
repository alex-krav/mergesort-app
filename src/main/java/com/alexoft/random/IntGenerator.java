package com.alexoft.random;

import java.io.File;

/**
 * Random integers generator interface.
 */
public interface IntGenerator {

    /**
     * Generates file of random integers with length n
     * @param fileName name of file
     * @param size size of array
     * @return file of random integers
     */
    File generate(String fileName, Integer size);

    /**
     * Generates file of random integers with length n,
     * with minimum and maximum values passed as arguments
     * @param fileName name of file
     * @param size size of array
     * @param min minimum value of integer to be generated (inclusive)
     * @param max maximum value of integer to be generated (inclusive)
     * @return file of random integers
     */
    File generate(String fileName, Integer size, Integer min, Integer max);
}
