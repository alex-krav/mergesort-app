package com.alexoft.algo;

import com.alexoft.random.IntGenerator;
import com.alexoft.random.IntGeneratorImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for comparison of sorting results
 * by three implementations of merge sort.
 */
public class SortingCorrectnessTest {

    private BinaryMergeSort topDownMergeSort;
    private NaturalMergeSort naturalMergeSort;
    private ThreeWayMergeSort threeWayMergeSort;
    private IntGenerator generator;

    @Before
    public void setUp() {
        topDownMergeSort = new BinaryMergeSort();
        naturalMergeSort = new NaturalMergeSort();
        threeWayMergeSort = new ThreeWayMergeSort();
        generator = new IntGeneratorImpl();
    }

    /**
     * Here we assume that top-down implementation is correct,
     * because it's algorithm was copied from trusted resource,
     * while natural and multiway were developed from scratch.
     */
    @Test
    public void equalResultsAscOrder() {
        // Given
        int num = 50000; boolean asc = true;
        int[] data = generator.generate(num);
        int[] topDownResult = new int[num]; System.arraycopy(data, 0, topDownResult, 0, num);
        int[] naturalResult = new int[num]; System.arraycopy(data, 0, naturalResult, 0, num);
        int[] threeWayResult = new int[num]; System.arraycopy(data, 0, threeWayResult, 0, num);

        // When
        topDownMergeSort.setAscending(asc); topDownMergeSort.sort(topDownResult);
        naturalMergeSort.setAscending(asc); naturalMergeSort.sort(naturalResult);
        threeWayMergeSort.setAscending(asc); threeWayMergeSort.sort(threeWayResult);

        // Then
        assertArrayEquals(topDownResult, naturalResult);
        assertArrayEquals(topDownResult, threeWayResult);
    }

    @Test
    public void equalResultsDescOrder() {
        // Given
        int num = 50000; boolean asc = false;
        int[] data = generator.generate(num);
        int[] topDownResult = new int[num]; System.arraycopy(data, 0, topDownResult, 0, num);
        int[] naturalResult = new int[num]; System.arraycopy(data, 0, naturalResult, 0, num);
        int[] threeWayResult = new int[num]; System.arraycopy(data, 0, threeWayResult, 0, num);

        // When
        topDownMergeSort.setAscending(asc); topDownMergeSort.sort(topDownResult);
        naturalMergeSort.setAscending(asc); naturalMergeSort.sort(naturalResult);
        threeWayMergeSort.setAscending(asc); threeWayMergeSort.sort(threeWayResult);

        // Then
        assertArrayEquals(topDownResult, naturalResult);
        assertArrayEquals(topDownResult, threeWayResult);
    }
}
