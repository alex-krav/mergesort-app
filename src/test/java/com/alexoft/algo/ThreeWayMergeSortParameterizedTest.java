package com.alexoft.algo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for TopDownImpl.java
 * Uses parameters for testing data.
 */
@RunWith(Parameterized.class)
public class ThreeWayMergeSortParameterizedTest {

    private MergeSort mergeSort;
    private final int[] data;
    private final int[] expected;

    public ThreeWayMergeSortParameterizedTest(int[] data, int[] expected) {
        this.data = data;
        this.expected = expected;
    }

    @Before
    public void setup() {
        mergeSort = new ThreeWayMergeSort();
    }

    @Parameterized.Parameters
    public static Collection<int[][]> data() {
        return Arrays.asList(new int[][][] {
                {{0},{0}},
                {{1,0},{0,1}},
                {{2,1,0},{0,1,2}},
                {{3,2,1,0},{0,1,2,3}},
                {{4,3,2,1,0},{0,1,2,3,4}},
                {{5,4,3,2,1,0},{0,1,2,3,4,5}},
                {{6,5,4,3,2,1,0},{0,1,2,3,4,5,6}},
                {{7,6,5,4,3,2,1,0},{0,1,2,3,4,5,6,7}},
                {{8,7,6,5,4,3,2,1,0},{0,1,2,3,4,5,6,7,8}},
                {{9,8,7,6,5,4,3,2,1,0},{0,1,2,3,4,5,6,7,8,9}},
                {{3,4,2,1,7,5,8,9,0,6},{0,1,2,3,4,5,6,7,8,9}},
                {{0,1},{0,1}},
                {{0,1,2},{0,1,2}},
                {{0,1,2,3},{0,1,2,3}},
                {{0,1,2,3,4},{0,1,2,3,4}},
                {{0,1,2,3,4,5},{0,1,2,3,4,5}},
                {{0,1,2,3,4,5,6},{0,1,2,3,4,5,6}},
                {{0,1,2,3,4,5,6,7},{0,1,2,3,4,5,6,7}},
                {{0,1,2,3,4,5,6,7,8},{0,1,2,3,4,5,6,7,8}},
                {{0,1,2,3,4,5,6,7,8,9},{0,1,2,3,4,5,6,7,8,9}}
        });
    }

    @Test
    public void sort() {
        // Given
        // When
        mergeSort.sort(data);
        // Then
        assertArrayEquals(expected, data);
    }
}
