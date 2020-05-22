package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Interface for logging interim and output results,
 * as well as algorithms statistics
 */
public interface Logger {
    // number format for algorithm complexity number
    DecimalFormat formatter = new DecimalFormat("#,###");

    /**
     * Prints text string and file with integers array.
     * @param text message string
     * @param file file with integers array
     */
    void print(String text, File file);

    /**
     * Prints text string
     * @param text message string
     */
    void print(String text);

    /**
     * Prints file with integers array
     * @param text text string
     * @param file file with integers arrray
     * @param size size of input array
     */
    void print(String text, File file, int size);

    /**
     * Prints list with integers
     * @param text text string
     * @param list list of integers
     * @param size size of input array
     */
    void print(String text, List<Integer> list, int size);

    /**
     * Logs statistics of an algorithm, after
     * method had finished sorting of array
     * @param stats statistics object
     */
    void print(AlgoStats stats);
}
