package com.alexoft.sorting;

import com.alexoft.algo.AlgoStats;
import com.alexoft.algo.MergeSort;
import com.alexoft.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Sorting interface
 */
public class SortingImpl implements Sorting {

    private List<AlgoStats> statResults = new ArrayList<>();
    private List<MergeSort> sorters = new ArrayList<>();
    private Logger consoleLog;
    private Logger fileLog;
    private Logger screenLog;

    @Override
    public void add(MergeSort mergeSort) {
        this.sorters.add(mergeSort);
    }

    /**
     * Method creates a copy of array before invoking new sort.
     * Output array is saved to file.
     * @param data integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    @Override
    public void process(int[] data, boolean asc) {
        statResults.clear();
        // log input data
        consoleLog.print("input array", data);
        screenLog.print("input array", data);
        // copy data
        int n = data.length;
        int[] copy = new int[n];

        for (MergeSort sorter : sorters) {
            System.arraycopy(data, 0, copy, 0, n);
            // apply sorting to array
            apply(sorter, copy, asc);
        }

        // log output data
        fileLog.print("output", copy);
        screenLog.print("\noutput array", copy);

        // log stats for sorting methods
        for(AlgoStats stats : statResults) {
            consoleLog.print(stats);
            screenLog.print(stats);
        }
    }

    /**
     * Method changes input array. Interim results and output array are logged.
     * @param sorter MergeSort implementation object
     * @param data integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    @Override
    public void apply(MergeSort sorter, int[] data, boolean asc) {

        // sort & log interim and final results
        sorter.setLogger(consoleLog);
        sorter.setAsc(asc);
        sorter.sort(data);

        // log stats
        statResults.add(sorter.getStats());
    }

    public void setConsoleLog(Logger consoleLog) {
        this.consoleLog = consoleLog;
    }

    public void setFileLog(Logger fileLog) {
        this.fileLog = fileLog;
    }

    public void setScreenLog(Logger screenLog) {
        this.screenLog = screenLog;
    }
}
