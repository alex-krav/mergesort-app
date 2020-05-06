package com.alexoft.service;

import com.alexoft.sorting.AlgoStats;
import com.alexoft.sorting.ArrayUtils;

/**
 * Implementation of LoggingService which uses Log interface
 */
public class LoggingServiceImpl implements LoggingService {
    private Log log;

    public LoggingServiceImpl(Log log) {
        this.log = log;
    }

    @Override
    public void log(String message, int[] numbers) {
        log.write(message);
        log.write(ArrayUtils.print(numbers));
    }

    @Override
    public void log(AlgoStats stats) {
        log.write(String.format("%s statistics", stats.getAlgoName()));
        log.write(String.format("elements: %d", stats.getArraySize()));
        log.write(String.format("copies: %d", stats.getCopies()));
        log.write(String.format("splits: %d", stats.getSplits()));
        log.write(String.format("merges: %d", stats.getMerges()));
        // TODO: calc O notation from N elements ?
    }
}
