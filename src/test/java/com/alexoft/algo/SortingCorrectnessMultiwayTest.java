package com.alexoft.algo;

import com.alexoft.random.IntGenerator;
import com.alexoft.random.IntGeneratorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for comparison of sorting results
 * by three implementations of merge sort.
 */
@RunWith(Parameterized.class)
public class SortingCorrectnessMultiwayTest {

    private BinaryMergeSort topDownMergeSort;
    private MultiwayMergeSort multiwayMergeSort;
    private IntGenerator generator;
    private int k;
    private boolean asc;

    public SortingCorrectnessMultiwayTest(int k, boolean asc) {
        this.k = k;
        this.asc = asc;
    }

    @Before
    public void setUp() {
        topDownMergeSort = new BinaryMergeSort();
        multiwayMergeSort = new MultiwayMergeSort();
        generator = new IntGeneratorImpl();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {2, true}, {2, false},
                {3, true}, {3, false},
                {4, true}, {4, false}
        });
    }

    /**
     * Here we assume that top-down implementation is correct,
     * because it's algorithm was copied from trusted resource,
     * while natural and multiway were developed from scratch.
     */
    @Test
    public void equalResults() {
        // Given
        int num = 50000;
        int[] data = {}; //generator.generate(num);
        int[] topDownResult = new int[num]; System.arraycopy(data, 0, topDownResult, 0, num);
        int[] multiwayResult = new int[num]; System.arraycopy(data, 0, multiwayResult, 0, num);

        // When
        topDownMergeSort.setAscending(asc); topDownMergeSort.sort(topDownResult);
        multiwayMergeSort.setAscending(asc); multiwayMergeSort.setK(k); multiwayMergeSort.sort(multiwayResult);

        // Then
        assertArrayEquals(topDownResult, multiwayResult);
    }
}
