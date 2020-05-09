package com.alexoft.service;

import com.alexoft.sorting.MultiwayMergeSortImpl;
import com.alexoft.sorting.NaturalMergeSortImpl;
import com.alexoft.sorting.TopDownImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * Test class for SortingServiceImpl.java
 */
public class SortingServiceImplTest {

    public static final String OUTPUT_FILE = "output_sorting_service.txt";
    private SortingServiceImpl sortingService;
    private IOService ioService;
    private Log log;
    private LoggingService logger;

    @Before
    public void setUp() {
        sortingService = new SortingServiceImpl();
        ioService = mock(IOService.class);
        sortingService.setIoService(ioService);
        log = mock(Log.class);
        logger = new LoggingServiceImpl(log);
        sortingService.setLogger(logger);
        sortingService.setOutputFile(OUTPUT_FILE);
    }

    @Test
    public void apply() throws IOException {
        // Given
        int[] data = {6,5,4,3,2,1};
        // When
        sortingService.apply(new TopDownImpl(), data);
        // Then
        Mockito.verify(log, times(1)).write("Merge sort is starting...");
        Mockito.verify(log, times(2)).write("");
        Mockito.verify(log, times(5)).write("Merge sort interim result");
        Mockito.verify(log, times(1)).write("[6 4 5 3 2 1]");
        Mockito.verify(log, times(1)).write("[4 5 6 3 2 1]");
        Mockito.verify(log, times(1)).write("[6 4 5 3 1 2]");
        Mockito.verify(log, times(1)).write("[4 5 6 1 2 3]");
        Mockito.verify(log, times(2)).write("[1 2 3 4 5 6]");
        Mockito.verify(log, times(1)).write("Merge sort output");

        Mockito.verify(ioService).writeFile(OUTPUT_FILE, data);
    }

    @Test
    public void process() throws IOException {
        // Given
        TopDownImpl mergeSort = new TopDownImpl();
        sortingService.add(mergeSort);
        sortingService.add(new NaturalMergeSortImpl());
        sortingService.add(new MultiwayMergeSortImpl(3));
        int[] data = {6,5,4,3,2,1};
        // When
        sortingService.process(data);
        // Then
        Mockito.verify(log, times(1)).write("input data");
        Mockito.verify(log, times(1)).write("Merge sort is starting...");
        Mockito.verify(log, times(1)).write("Natural merge sort is starting...");
        Mockito.verify(log, times(1)).write("Multiway merge sort (k=3) is starting...");
        Mockito.verify(log, times(11)).write("");

        Mockito.verify(log, times(5)).write("Merge sort interim result");
        Mockito.verify(log, times(3)).write("Natural merge sort interim result");
        Mockito.verify(log, times(4)).write("Multiway merge sort (k=3) interim result");

        Mockito.verify(log, times(1)).write("[6 5 4 3 2 1]");
        Mockito.verify(log, times(1)).write("[6 4 5 3 2 1]");
        Mockito.verify(log, times(1)).write("[4 5 6 3 2 1]");
        Mockito.verify(log, times(1)).write("[6 4 5 3 1 2]");
        Mockito.verify(log, times(1)).write("[4 5 6 1 2 3]");

        Mockito.verify(log, times(6)).write("[1 2 3 4 5 6]");
        Mockito.verify(log, times(1)).write("Merge sort output");
        Mockito.verify(log, times(1)).write("Natural merge sort output");
        Mockito.verify(log, times(1)).write("Multiway merge sort (k=3) output");

        Mockito.verify(log, times(1)).write("Merge sort statistics");
        Mockito.verify(log, times(1)).write("Natural merge sort statistics");
        Mockito.verify(log, times(1)).write("Multiway merge sort (k=3) statistics");
        Mockito.verify(log, times(3)).write("elements: 6");

        Mockito.verify(log, times(1)).write("copies: 1");
        Mockito.verify(log, times(1)).write("copies: 4");
        Mockito.verify(log, times(1)).write("copies: 5");

        Mockito.verify(log, times(1)).write("splits: 5");
        Mockito.verify(log, times(1)).write("splits: 1");
        Mockito.verify(log, times(1)).write("splits: 4");

        Mockito.verify(log, times(1)).write("merges: 5");
        Mockito.verify(log, times(1)).write("merges: 7");
        Mockito.verify(log, times(1)).write("merges: 4");
        Mockito.verify(log, times(1)).write(String.format("output saved to %s", OUTPUT_FILE));

        mergeSort.sort(data);
        Mockito.verify(ioService).writeFile(OUTPUT_FILE, data);
    }
}