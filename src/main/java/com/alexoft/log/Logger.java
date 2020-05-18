package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.File;

/**
 * Interface for logging interim and output results,
 * as well as algorithms statistics
 */
public interface Logger {
    /**
     * Prints text string and integers array.
     * @param text message string
     * @param numbers integers array
     */
    void print(String text, File file);

    /**
     * Prints text string
     * @param text message string
     */
    void print(String text);

    /**
     * Prints integers array
     * @param numbers integers array
     */
    void print(String text, File file, int size);

    /**
     * Logs statistics of an algorithm, after
     * method had finished sorting of array
     * @param stats statistics object
     */
    void print(AlgoStats stats);
}
