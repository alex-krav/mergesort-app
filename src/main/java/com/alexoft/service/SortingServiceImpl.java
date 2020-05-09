package com.alexoft.service;

import com.alexoft.sorting.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SortingService interface
 */
public class SortingServiceImpl implements SortingService {

    private List<AlgoStats> statResults = new ArrayList<>();
    private List<MergeSort> sorters = new ArrayList<>();
    private boolean saved;
    private String outputFile;
    private IOService ioService;
    private LoggingService logger;

    public SortingServiceImpl() {}

    @Override
    public void add(MergeSort mergeSort) {
        this.sorters.add(mergeSort);
    }

    @Override
    public void process(int[] data) throws IOException {
        // log input data
        logger.log("input data", data);

        statResults.clear();
        for (MergeSort sorter : sorters) {
            // copy data
            int n = data.length;
            int[] copy = new int[n];
            System.arraycopy(data, 0, copy, 0, n);
            // apply sorting to array
            apply(sorter, copy);
        }

        // log output file name
        logger.log(String.format("output saved to %s", outputFile));

        // log stats for sorting methods
        for(AlgoStats stats : statResults)
            logger.log(stats);
    }

    @Override
    public void apply(MergeSort sorter, int[] data) throws IOException {

        // sort & log interim and final results
        sorter.setLogger(logger);
        sorter.sort(data);

        // log stats
        statResults.add(sorter.getStats());

        // save to file (once)
        if (!saved) {
            ioService.writeFile(outputFile, data);
            saved = true;
        }
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void setIoService(IOService ioService) {
        this.ioService = ioService;
    }

    public void setLogger(LoggingService logger) {
        this.logger = logger;
    }
}
