package com.alexoft.sorting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for NaturalMergeSortImplTest.java
 * Uses parameters for testing data.
 */
@RunWith(Parameterized.class)
public class NaturalMergeSortImplTest {

    private MergeSort mergeSort;
    private final int[] data;

    public NaturalMergeSortImplTest(int[] data) {
        this.data = data;
    }

    @Before
    public void setup() {
        mergeSort = new NaturalMergeSortImpl();
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
    public void sort() {
        // Given
        // When
        mergeSort.sort(data);
        // Then
        int[] expected = {0,1,2,3,4,5,6,7,8,9};
        assertArrayEquals(expected, data);
    }
}
