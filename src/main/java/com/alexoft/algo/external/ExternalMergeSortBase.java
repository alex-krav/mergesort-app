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
    // number of logged interim arrays
    protected int displayedCounter;

    /**
     * Clears counter before every sort
     */
    protected void initInterimResultCounters() {
        displayedCounter = 0;
    }

    /**
     * Logs interim array
     * @param message text
     * @param array file with integers
     * @param size size of array in file
     */
    protected void logInterim(String message, File array, int size) {
        if (null != logger) {
            ++displayedCounter;
            logger.print(String.format(message + " %d", displayedCounter), array, size);
        }
    }

    /**
     * Logs interim array
     * @param message text
     * @param list list of integers
     * @param size overall size of input array
     */
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
     * @param logger logger object
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
     * Logs a message file of integers
     * @param message message string
     * @param numbers file with integers array
     */
    public void log(String message, File numbers) {
        if (null != logger)
            logger.print(message, numbers);
    }

    /**
     * Gets sorting order
     * @return string representation of sorting order
     */
    protected String getAscString() {
        return isAscending() ? "(ascending order)" : "(descending order)";
    }
}
