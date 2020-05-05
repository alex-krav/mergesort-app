package com.alexoft.sorting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for MultiwayMergeSortImpl.java
 * Uses parameters for testing data.
 */
@RunWith(Parameterized.class)
public class MultiwayMergeSortImplTest {

    private MultiwayMergeSortImpl mergeSort;
    private final int[] data;
    private final int[] expected = {0,1,2,3,4,5,6,7,8,9};

    public MultiwayMergeSortImplTest(int[] data) {
        this.data = data;
    }

    @Before
    public void setup() {
        mergeSort = new MultiwayMergeSortImpl();
    }

    @Parameterized.Parameters
    public static Collection<int[][]> data() {
        return Arrays.asList(new int[][][] {
                {{3,4,2,1,7,5,8,9,0,6}},
                {{0,1,2,3,4,5,6,7,8,9}},
                {{9,8,7,6,5,4,3,2,1,0}}
        });
    }

    @Test
    public void sortK2default() {
        // Given
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }

    @Test
    public void sortK2() {
        // Given
        mergeSort.setK(2);
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }

    @Test
    public void sortK3() {
        // Given
        mergeSort.setK(3);
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }

    @Test
    public void sortK4() {
        // Given
        mergeSort.setK(4);
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }
}
