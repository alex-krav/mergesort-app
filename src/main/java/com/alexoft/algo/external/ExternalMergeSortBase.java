package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;
import com.alexoft.log.Logger;

import java.io.File;
import java.util.List;

/**
 * Base class for merge sort implementations.
 * It encapsulates logging and statistics.
 */
public abstract class ExternalMergeSortBase implements ExternalMergeSort {
    protected AlgoStats algoStats;
    protected Logger logger;
    // sorts in ascending order by default
    protected boolean asc = true;
    // variables for limiting number of logged interim arrays
    protected int displayedCounter;

    protected void initInterimResultCounters() {
        displayedCounter = 0;
    }

    /**
     * Logs interim array
     * @param array integers array
     */
    protected void logInterim(String message, File array, int size) {
        if (null != logger) {
            ++displayedCounter;
            logger.print(String.format(message + " %d", displayedCounter), array, size);
        }
    }

    protected void logInterim(String message, List<Integer> list, int size) {
        if (null != logger) {
            ++displayedCounter;
            logger.print(String.format(message + " %d", displayedCounter), list, size);
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
    public void log(String message, File numbers) {
        if (null != logger)
            logger.print(message, numbers);
    }

    protected String getAscString() {
        return isAscending() ? "(ascending order)" : "(descending order)";
    }
}
