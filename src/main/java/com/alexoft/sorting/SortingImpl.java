package com.alexoft.sorting;

import com.alexoft.algo.AlgoStats;
import com.alexoft.algo.external.ExternalMergeSort;
import com.alexoft.algo.external.ExternalMergeSortBase;
import com.alexoft.log.Logger;
import com.alexoft.log.ScreenLogger;
import com.alexoft.log.TerminalLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.alexoft.common.FileUtils.copy;

/**
 * Implementation of Sorting interface
 */
public class SortingImpl implements Sorting {

    private List<AlgoStats> statResults = new ArrayList<>();
    private List<ExternalMergeSort> sorters = new ArrayList<>();
    private Logger terminalLog;
    private Logger screenLog;
    private boolean lastSorter;

    @Override
    public void add(ExternalMergeSort mergeSort) {
        this.sorters.add(mergeSort);
    }

    @Override
    public void process(File inputFile, boolean asc) {
        // clear statistics of previous run
        statResults.clear();
        // log input data
        terminalLog.print("input array", inputFile);
        screenLog.print("input array", inputFile);

        lastSorter = false;
        for (int i = 0; i < sorters.size(); ++i) {
            if (i == sorters.size()-1)
                lastSorter = true;
            // apply sorting to array
            apply(sorters.get(i), inputFile, asc);
        }

        // log stats for sorting methods
        for(AlgoStats stats : statResults) {
            terminalLog.print(stats);
            screenLog.print(stats);
        }
    }

    /**
     * Method creates a copy of file before invoking sort. If it's not last sorting,
     * copy file is removed, otherwise saved with sorting result.
     * @param sorter ExternalMergeSort implementation object
     * @param originalFile file with integer array
     * @param asc boolean flag (true - ascending order, false - descending order)
     */
    @Override
    public void apply(ExternalMergeSort sorter, File originalFile, boolean asc) {
        File copyFile = copy(originalFile);

        ExternalMergeSortBase baseSorter = (ExternalMergeSortBase) sorter;
        // sort & log interim and final results
        baseSorter.setLogger(terminalLog);
        baseSorter.setAscending(asc);
        baseSorter.sort(copyFile);

        // log output data
        if (lastSorter)
            screenLog.print("\noutput array", copyFile);
        else if (!copyFile.delete())
            terminalLog.print("Couldn't remove copy of input file " + copyFile.getName());
        // log stats
        statResults.add(baseSorter.getStats());
    }

    public void setTerminalLog(TerminalLogger terminalLog) {
        this.terminalLog = terminalLog;
    }

    public void setScreenLog(ScreenLogger screenLog) {
        this.screenLog = screenLog;
    }
}
