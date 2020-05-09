package com.alexoft.service;

import java.util.Random;

/**
 * Random number generator class, that uses java.util.Random under the hood
 */
public class GeneratorServiceImpl implements GeneratorService {

    private Random rand;
    private Integer min;
    private Integer max;

    /**
     * Default constructor
     */
    public GeneratorServiceImpl() {
        this.rand = new Random(System.nanoTime());
    }

    /**
     * Constructor used when number range is required
     * @param min minimal number (inclusive)
     * @param max maximum number (inclusive)
     */
    public GeneratorServiceImpl(int min, int max) {
        this.rand = new Random(System.nanoTime());
        this.min = min;
        this.max = max;
    }

    /**
     * Constructor used when custom seed is required. The seed is the initial
     * value of the internal state of the pseudorandom number generator.
     * @param seed seed number
     * @param min minimal number (inclusive)
     * @param max maximum number (inclusive)
     */
    public GeneratorServiceImpl(long seed, int min, int max) {
        this.rand = new Random(seed);
        this.min = min;
        this.max = max;
    }

    /**
     * Defines a default initialized array,
     * then fills it with random numbers
     * @param n size of array
     * @return array of random integers
     */
    @Override
    public int[] generate(Integer n) {
        int[] result = new int[n];
        for (int i = 0; i < n; ++i) {
            result[i] = getNext();
        }
        return result;
    }

    @Override
    public int[] generate(Integer n, Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return generate(n);
    }

    /**
     * Generates random integer with or without min/max boundaries.
     * Method is concurrency safe to prevent threads for getting same number.
     * @return random int
     */
    private synchronized int getNext() {
        if (min == null)
            return rand.nextInt();
        else
            return rand.nextInt((max - min) + 1) + min;
    }
}
