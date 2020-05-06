package com.alexoft.service;

import com.alexoft.sorting.AlgoStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * Test class for LoggingServiceImpl.java
 * Mocks Log interface to verify whether it's
 * methods were called when using LoggingService
 */
public class LoggingServiceImplTest {

    private LoggingService logger;
    private Log log;

    @Before
    public void setUp() {
        log = mock(Log.class);
        logger = new LoggingServiceImpl(log);
    }

    @Test
    public void logAlgoStats() {
        // Given
        AlgoStats stats = new AlgoStats("Test sort");
        stats.setArraySize(10);
        stats.addMerges();
        stats.addCopies(); stats.addCopies();
        stats.addSplits(); stats.addSplits(); stats.addSplits();
        // When
        logger.log(stats);
        // Then
        Mockito.verify(log).write("Test sort statistics");
        Mockito.verify(log).write("elements: 10");
        Mockito.verify(log).write("copies: 2");
        Mockito.verify(log).write("splits: 3");
        Mockito.verify(log).write("merges: 1");
    }

    @Test
    public void logArray() {
        // Given
        int[] data = {1,2,3,4,5};
        // When
        logger.log("output", data);
        // Then
        Mockito.verify(log).write("output");
        Mockito.verify(log).write("[1 2 3 4 5]");
    }
}