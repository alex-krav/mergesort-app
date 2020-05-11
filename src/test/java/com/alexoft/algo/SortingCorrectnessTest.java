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
public class SortingCorrectnessTest {

    private TopDownImpl topDownMergeSort;
    private NaturalMergeSortImpl naturalMergeSort;
    private MultiwayMergeSortImpl multiwayMergeSort;
    private IntGenerator generator;
    private int k;

    public SortingCorrectnessTest(int k) {
        this.k = k;
    }

    @Before
    public void setUp() {
        topDownMergeSort = new TopDownImpl();
        naturalMergeSort = new NaturalMergeSortImpl();
        multiwayMergeSort = new MultiwayMergeSortImpl();
        generator = new IntGeneratorImpl();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {2},{3},{4},{5},{6},{7},{8}
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
        int[] data = generator.generate(num);
        int[] topDownResult = new int[num]; System.arraycopy(data, 0, topDownResult, 0, num);
        int[] naturalResult = new int[num]; System.arraycopy(data, 0, naturalResult, 0, num);
        int[] multiwayResult = new int[num]; System.arraycopy(data, 0, multiwayResult, 0, num);

        // When
        topDownMergeSort.sort(topDownResult);
        naturalMergeSort.sort(naturalResult);
        multiwayMergeSort.setK(k); multiwayMergeSort.sort(multiwayResult);

        // Then
        assertArrayEquals(topDownResult, naturalResult);
        assertArrayEquals(topDownResult, multiwayResult);
    }
}
