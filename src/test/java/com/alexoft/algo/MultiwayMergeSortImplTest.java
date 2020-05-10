package com.alexoft.algo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for MultiwayMergeSortImpl.java
 */
public class MultiwayMergeSortImplTest {

    private MultiwayMergeSortImpl mergeSort;

    @Before
    public void setup() {
        mergeSort = new MultiwayMergeSortImpl();
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
