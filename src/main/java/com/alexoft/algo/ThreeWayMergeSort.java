package com.alexoft.algo;

/**
 *  3-way merge is a specific type of sequence merge algorithms that specializes
 *  in taking in 3 sorted lists and merging them into a single sorted list.
 */
public class ThreeWayMergeSort extends MergeSortBase {

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
        tripleSplit(fArray, 0, gArray.length, gArray); // sort function
        CopyArray(fArray, gArray); // copy back elements of duplicate array
        algoStats.addCopies(); algoStats.addCopies();
        algoStats.setArraySize(n);
        log("3-way merge sort output", gArray);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Performing the merge sort algorithm on the given array of values
     * in the range of indices [low, high). low is minimum index, high is
     * maximum index (exclusive)
     * @param gArray source array
     * @param low lowest index (inclusive)
     * @param high highest index (exclusive)
     * @param destArray destination array
     */
    private void tripleSplit(int[] gArray, int low, int high,
                            int[] destArray) {
        algoStats.addSplits();
        // If array size is 1 then do nothing
        if (high - low < 2)
            return;
        // Splitting array into 3 parts
        int mid1 = low + ((high - low) / 3);
        int mid2 = low + 2 * ((high - low) / 3) + 1;
        // Sorting 3 arrays recursively
        tripleSplit(destArray, low, mid1, gArray);
        tripleSplit(destArray, mid1, mid2, gArray);
        tripleSplit(destArray, mid2, high, gArray);
        // Merging the sorted arrays
        tripleMerge(destArray, low, mid1, mid2, high, gArray);
    }

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
    private void tripleMerge(int[] gArray, int low,
                             int mid1, int mid2, int high,
                             int[] destArray) {
        int i = low, j = mid1, k = mid2, l = low;
        // choose smaller of the smallest in the three ranges
        while ((i < mid1) && (j < mid2) && (k < high)) {
            if (gArray[i] < gArray[j] == isAscending()) {
                if (gArray[i] < gArray[k] == isAscending())
                    destArray[l++] = gArray[i++];
                else
                    destArray[l++] = gArray[k++];
            } else {
                if (gArray[j] < gArray[k] == isAscending())
                    destArray[l++] = gArray[j++];
                else
                    destArray[l++] = gArray[k++];
            }
        }
        // case where first and second ranges have
        // remaining values
        while ((i < mid1) && (j < mid2)) {
            if (gArray[i] < gArray[j] == isAscending())
                destArray[l++] = gArray[i++];
            else
                destArray[l++] = gArray[j++];
        }
        // case where second and third ranges have
        // remaining values
        while ((j < mid2) && (k < high)) {
            if (gArray[j] < gArray[k] == isAscending())
                destArray[l++] = gArray[j++];

            else
                destArray[l++] = gArray[k++];
        }
        // case where first and third ranges have
        // remaining values
        while ((i < mid1) && (k < high)) {
            if (gArray[i] < gArray[k] == isAscending())
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

        logInterim("3-way merge sort interim result", destArray);
        algoStats.addMerges();
    }
}