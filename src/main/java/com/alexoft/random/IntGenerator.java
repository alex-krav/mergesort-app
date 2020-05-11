package com.alexoft.random;

/**
 * Random integers generator interface.
 */
public interface IntGenerator {

    /**
     * Generates array of random integers with length n
     * @param n size of array
     * @return array of random integers
     */
    int[] generate(Integer n);

    /**
     * Generates array of random integers with length n,
     * with minimum and maximum values passed as arguments
     * @param n size of array
     * @param min minimum value of integer to be generated (inclusive)
     * @param max maximum value of integer to be generated (inclusive)
     * @return array of random integers
     */
    int[] generate(Integer n, Integer min, Integer max);
}
