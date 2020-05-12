package com.alexoft.algo;

import com.alexoft.log.Logger;

import static com.alexoft.log.TerminalLogger.MAX_INTERIM_RESULTS;

/**
 *  3-way merge is a specific type of sequence merge algorithms that specializes
 *  in taking in 3 sorted lists and merging them into a single sorted list.
 */
public class ThreeWayMergeSort implements MergeSort {
    private AlgoStats algoStats;
    private Logger logger;
    // sorts in ascending order by default
    private boolean asc = true;
    // variables for limiting number of logged interim arrays
    private int logCounter, displayedCounter, divider;

    @Override
    public void sort(int[] gArray) {
        long startTime = System.nanoTime();
        if (gArray == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = gArray.length;
        if (n == 0)
            return;
        log("3-way merge sort is starting...");
        algoStats = new AlgoStats("3-way merge sort");
        initInterimResultCounters(n);
        int[] fArray = new int[n];
        CopyArray(gArray, fArray); // duplicate array
        mergeSort3WayRec(fArray, 0, gArray.length, gArray); // sort function
        CopyArray(fArray, gArray); // copy back elements of duplicate array
        algoStats.addCopies(); algoStats.addCopies();
        algoStats.setArraySize(n);
        log("3-way merge sort output", gArray);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Initializes variables for limiting number of logged interim arrays.
     * Maximum 50 interim arrays will be displayed.
     * @param length array length
     */
    private void initInterimResultCounters(int length) {
        logCounter = displayedCounter = 0;
        divider = length / MAX_INTERIM_RESULTS;
        if (divider == 0) divider = 1;
    }

    /*  */

    /**
     * Performing the merge sort algorithm on the given array of values
     * in the rangeof indices [low, high). low is minimum index, high is
     * maximum index (exclusive)
     * @param gArray source array
     * @param low lowest index (inclusive)
     * @param high highest index (exclusive)
     * @param destArray destination array
     */
    public void mergeSort3WayRec(int[] gArray, int low, int high,
                                 int[] destArray) {
        algoStats.addSplits();
        // If array size is 1 then do nothing
        if (high - low < 2)
            return;
        // Splitting array into 3 parts
        int mid1 = low + ((high - low) / 3);
        int mid2 = low + 2 * ((high - low) / 3) + 1;
        // Sorting 3 arrays recursively
        mergeSort3WayRec(destArray, low, mid1, gArray);
        mergeSort3WayRec(destArray, mid1, mid2, gArray);
        mergeSort3WayRec(destArray, mid2, high, gArray);
        // Merging the sorted arrays
        merge(destArray, low, mid1, mid2, high, gArray);
    }

    /* Merge the sorted ranges [low, mid1), [mid1,
       mid2) and [mid2, high) mid1 is first midpoint
       index in overall range to merge mid2 is second
       midpoint index in overall range to merge*/

    /**
     * Merge the sorted ranges [low, mid1), [mid1, mid2) and [mid2, high)
     * mid1 is first midpoint index in overall range to merge
     * mid2 is second midpoint index in overall range to merge
     * @param gArray source array
     * @param low lowest index (inclusive)
     * @param mid1 first midpoint index
     * @param mid2 second midpoint index
     * @param high highest index (exclusive)
     * @param destArray destination array
     */
    public void merge(int[] gArray, int low,
                         int mid1, int mid2, int high,
                         int[] destArray) {
        int i = low, j = mid1, k = mid2, l = low;
        // choose smaller of the smallest in the three ranges
        while ((i < mid1) && (j < mid2) && (k < high)) {
            if (gArray[i] < gArray[j] == getAsc()) {
                if (gArray[i] < gArray[k] == getAsc())
                    destArray[l++] = gArray[i++];
                else
                    destArray[l++] = gArray[k++];
            } else {
                if (gArray[j] < gArray[k] == getAsc())
                    destArray[l++] = gArray[j++];
                else
                    destArray[l++] = gArray[k++];
            }
        }
        // case where first and second ranges have
        // remaining values
        while ((i < mid1) && (j < mid2)) {
            if (gArray[i] < gArray[j] == getAsc())
                destArray[l++] = gArray[i++];
            else
                destArray[l++] = gArray[j++];
        }
        // case where second and third ranges have
        // remaining values
        while ((j < mid2) && (k < high)) {
            if (gArray[j] < gArray[k] == getAsc())
                destArray[l++] = gArray[j++];

            else
                destArray[l++] = gArray[k++];
        }
        // case where first and third ranges have
        // remaining values
        while ((i < mid1) && (k < high)) {
            if (gArray[i] < gArray[k] == getAsc())
                destArray[l++] = gArray[i++];
            else
                destArray[l++] = gArray[k++];
        }
        // copy remaining values from the first range
        while (i < mid1)
            destArray[l++] = gArray[i++];
        // copy remaining values from the second range
        while (j < mid2)
            destArray[l++] = gArray[j++];
        // copy remaining values from the third range
        while (k < high)
            destArray[l++] = gArray[k++];

        if (++logCounter % divider == 0) {
            ++displayedCounter;
            log(String.format("3-way merge sort interim result %d (%d)", logCounter, displayedCounter), destArray);
        }
        algoStats.addMerges();
    }

    @Override
    public AlgoStats getStats() {
        return algoStats;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    @Override
    public boolean getAsc() {
        return asc;
    }

    @Override
    public void log(String message) {
        if (null != logger)
            logger.print(message);
    }

    @Override
    public void log(String message, int[] numbers) {
        if (null != logger)
            logger.print(message, numbers);
    }
}