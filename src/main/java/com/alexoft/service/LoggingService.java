package com.alexoft.service;

import com.alexoft.sorting.AlgoStats;

/**
 * Class for logging interim and output results,
 * as well as algorithms statistics
 */
public interface LoggingService {

    /**
     * Logs message and array of numbers
     * @param message message string
     * @param numbers integers array
     */
    void log(String message, int[] numbers);

    /**
     * Logs statistics of an algorithm, after
     * method had finished sorting of array
     * @param stats statistics object
     */
    void log(AlgoStats stats);
}
