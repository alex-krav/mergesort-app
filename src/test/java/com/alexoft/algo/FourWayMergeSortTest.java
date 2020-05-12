package com.alexoft.algo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for TopDownImpl.java
 */
public class FourWayMergeSortTest {

    private MergeSort mergeSort;

    @Before
    public void setup() {
        mergeSort = new FourWayMergeSort();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortNullArray() {
        // Given
        int[] data = null;
        // When
        mergeSort.sort(data);
    }

    @Test
    public void sortEmptyArray() {
        // Given
        int[] data = {}, expected = {};
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }
}
