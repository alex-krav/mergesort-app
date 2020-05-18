package com.alexoft.random;

import java.io.File;

/**
 * Random integers generator interface.
 */
public interface IntGenerator {

    /**
     * Generates file of random integers with length n
     * @param size size of array
     * @param fileName name of file
     * @return file of random integers
     */
    File generate(String fileName, Integer size);

    /**
     * Generates file of random integers with length n,
     * with minimum and maximum values passed as arguments
     * @param size size of array
     * @param fileName name of file
     * @param min minimum value of integer to be generated (inclusive)
     * @param max maximum value of integer to be generated (inclusive)
     * @return file of random integers
     */
    File generate(String fileName, Integer size, Integer min, Integer max);
}
