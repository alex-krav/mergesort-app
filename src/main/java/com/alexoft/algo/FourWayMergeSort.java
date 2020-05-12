package com.alexoft.algo;

/**
 *  4-way merge is a specific type of sequence merge algorithms that specializes
 *  in taking in 4 sorted lists and merging them into a single sorted list.
 */
public class FourWayMergeSort extends MergeSortBase {

    @Override
    public void sort(int[] gArray) {
        long startTime = System.nanoTime();
        if (gArray == null)
            throw new IllegalArgumentException("Null arrays not allowed");
        int n = gArray.length;
        if (n == 0)
            return;
        log("4-way merge sort is starting...");
        algoStats = new AlgoStats("4-way merge sort");
        initInterimResultCounters(n);
        int[] fArray = new int[n];
        CopyArray(gArray, fArray); // duplicate array
        quarterSplit(fArray, 0, gArray.length, gArray); // sort function
        CopyArray(fArray, gArray); // copy back elements of duplicate array
        algoStats.addCopies(); algoStats.addCopies();
        algoStats.setArraySize(n);
        log("4-way merge sort output", gArray);
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
    private void quarterSplit(int[] gArray, int low, int high,
                              int[] destArray){
        algoStats.addSplits();
        // If array size is 1 then do nothing
        if (high - low < 2)
            return;
        // Splitting array into 4 parts
        int mid1 = low + ((high - low) / 4);
        int mid2 = low + ((high - low) / 2);
        int mid3 = low + ((high - low) / 4) + ((high - low) / 2);
        // Sorting 4 arrays recursively
        quarterSplit(destArray, low, mid1, gArray);
        quarterSplit(destArray, mid1, mid2, gArray);
        quarterSplit(destArray, mid2, mid3, gArray);
        quarterSplit(destArray, mid3, high, gArray);
        // Merging the sorted arrays
        quarterMerge(destArray, low, mid1, mid2, mid3, high, gArray);
    }

    /**
     * Merge the sorted ranges [low, mid1), [mid1, mid2), [mid2, mid3) and [mid3, high)
     * @param gArray source array
     * @param low lowest index (inclusive)
     * @param mid1 first midpoint index
     * @param mid2 second midpoint index
     * @param mid3 third midpoint index
     * @param high highest index (exclusive)
     * @param destArray destination array
     */
    private void quarterMerge(int[] gArray, int low,
                              int mid1, int mid2, int mid3,
                              int high, int[] destArray) {
        int i1 = low, i2 = mid1, i3 = mid2, i4 = mid3, id = low;
        // choose smaller of the smallest in the four ranges
        while ((i1 < mid1) && (i2 < mid2) && (i3 < mid3) && (i4 < high)) {
            if (gArray[i1] < gArray[i2] && gArray[i1] < gArray[i3] && gArray[i1] < gArray[i4])
                destArray[id++] = gArray[i1++];
            else if (gArray[i2] < gArray[i3] && gArray[i2] < gArray[i4])
                destArray[id++] = gArray[i2++];
            else if (gArray[i3] < gArray[i4])
                destArray[id++] = gArray[i3++];
            else
                destArray[id++] = gArray[i4++];
        }
        // choose smaller in the three range
        while ((i1 < mid1) && (i2 < mid2) && (i3 < mid3)) {
            if (gArray[i1] < gArray[i2] && gArray[i1] < gArray[i3])
                destArray[id++] = gArray[i1++];
            else if (gArray[i2] < gArray[i3])
                destArray[id++] = gArray[i2++];
            else
                destArray[id++] = gArray[i3++];
        }
        while ((i1 < mid1) && (i2 < mid2) && (i4 < high)) {
            if (gArray[i1] < gArray[i2] && gArray[i1] < gArray[i4])
                destArray[id++] = gArray[i1++];
            else if (gArray[i2] < gArray[i4])
                destArray[id++] = gArray[i2++];
            else
                destArray[id++] = gArray[i4++];
        }
        while ((i2 < mid2) && (i3 < mid3) && (i4 < high)) {
            if (gArray[i2] < gArray[i3] && gArray[i2] < gArray[i4])
                destArray[id++] = gArray[i2++];
            else if (gArray[i3] < gArray[i4])
                destArray[id++] = gArray[i3++];
            else
                destArray[id++] = gArray[i4++];
        }
        while ((i1 < mid1) && (i3 < mid3) && (i4 < high)) {
            if (gArray[i1] < gArray[i3] && gArray[i1] < gArray[i4])
                destArray[id++] = gArray[i1++];
            else if (gArray[i3] < gArray[i4])
                destArray[id++] = gArray[i3++];
            else
                destArray[id++] = gArray[i4++];
        }
        // choose smaller in the two range
        while ((i1 < mid1) && (i2 < mid2)) {
            if (gArray[i1] < gArray[i2])
                destArray[id++] = gArray[i1++];
            else
                destArray[id++] = gArray[i2++];
        }
        while ((i1 < mid1) && (i3 < mid3)) {
            if (gArray[i1] < gArray[i3])
                destArray[id++] = gArray[i1++];
            else
                destArray[id++] = gArray[i3++];
        }
        while ((i1 < mid1) && (i4 < high)) {
            if (gArray[i1] < gArray[i4])
                destArray[id++] = gArray[i1++];
            else
                destArray[id++] = gArray[i4++];
        }
        while ((i2 < mid2) && (i3 < mid3)) {
            if (gArray[i2] < gArray[i3])
                destArray[id++] = gArray[i2++];
            else
                destArray[id++] = gArray[i3++];
        }
        while ((i2 < mid2) && (i4 < high)) {
            if (gArray[i2] < gArray[i4])
                destArray[id++] = gArray[i2++];
            else
                destArray[id++] = gArray[i4++];
        }
        while ((i3 < mid3) && (i4 < high)) {
            if (gArray[i3] < gArray[i4])
                destArray[id++] = gArray[i3++];
            else
                destArray[id++] = gArray[i4++];
        }
        // copy remaining values from the first range
        while (i1 < mid1)
            destArray[id++] = gArray[i1++];
        // copy remaining values from the second range
        while (i2 < mid2)
            destArray[id++] = gArray[i2++];
        // copy remaining values from the third range
        while (i3 < mid3)
            destArray[id++] = gArray[i3++];
        // copy remaining values from the fourth range
        while (i4 < high)
            destArray[id++] = gArray[i4++];

        logInterim("4-way merge sort interim result", destArray);
        algoStats.addMerges();
    }
}