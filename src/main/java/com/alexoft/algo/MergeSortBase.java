package com.alexoft.algo;

import com.alexoft.log.Logger;

import static com.alexoft.log.TerminalLogger.MAX_INTERIM_RESULTS;

/**
 * Base class for merge sort implementations.
 * It incapsulates logging and statistics.
 */
public abstract class MergeSortBase implements MergeSort {
    protected AlgoStats algoStats;
    protected Logger logger;
    // sorts in ascending order by default
    protected boolean asc = true;
    // variables for limiting number of logged interim arrays
    protected int logCounter, displayedCounter, divider;

    /**
     * Initializes variables for limiting number of logged interim arrays.
     * Maximum 50 interim arrays will be displayed.
     * @param length array length
     */
    protected void initInterimResultCounters(int length) {
        logCounter = displayedCounter = 0;
        divider = length / MAX_INTERIM_RESULTS;
        if (divider == 0) divider = 1;
    }

    /**
     * Logs interim array
     * @param array integers array
     */
    protected void logInterim(String message, int[] array) {
        if (++logCounter % divider == 0) {
            ++displayedCounter;
            log(String.format(message + " %d (%d)", logCounter, displayedCounter), array);
        }
    }

    /**
     * Gets statistics object for algorithm
     * @return AlgoStats object
     */
    public AlgoStats getStats() {
        return algoStats;
    }

    /**
     * Setter for logger
     * @param logger LoggingService
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Sets order of sorting
     * @param asc boolean value, if false - sorts in descending order
     */
    public void setAscending(boolean asc) {
        this.asc = asc;
    }

    /**
     * Gets an order of sorting
     * @return true for ascending, false for descending
     */
    public boolean isAscending() {
        return asc;
    }

    /**
     * Logs a message
     * @param message message string
     */
    public void log(String message) {
        if (null != logger)
            logger.print(message);
    }

    /**
     * Logs a message and array
     * @param message message string
     * @param numbers integer array
     */
    public void log(String message, int[] numbers) {
        if (null != logger)
            logger.print(message, numbers);
    }
}
