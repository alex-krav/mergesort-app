package com.alexoft.random;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static com.alexoft.common.StringUtils.generateFilename;

/**
 * Random number generator class, that uses java.util.Random under the hood
 */
public class IntGeneratorImpl implements IntGenerator {

    private final Random rand;
    private Integer min;
    private Integer max;

    /**
     * Default constructor
     */
    public IntGeneratorImpl() {
        this.rand = new Random(System.nanoTime());
    }

    /**
     * Constructor used when number range is required
     * @param min minimal number (inclusive)
     * @param max maximum number (inclusive)
     */
    public IntGeneratorImpl(int min, int max) {
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
    public IntGeneratorImpl(long seed, int min, int max) {
        this.rand = new Random(seed);
        this.min = min;
        this.max = max;
    }

    @Override
    public File generate(String fileName, Integer size) {
        File file = new File(generateFilename(fileName));

        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(size + "\n");
            for (int i = 0; i < size; ++i)
                out.write(getNext() + " ");
            out.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public File generate(String fileName, Integer size, Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return generate(fileName, size);
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
