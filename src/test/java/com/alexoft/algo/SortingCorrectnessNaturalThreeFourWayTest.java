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
public class SortingCorrectnessNaturalThreeFourWayTest {

    private BinaryMergeSort topDownMergeSort;
    private NaturalMergeSort naturalMergeSort;
    private ThreeWayMergeSort threeWayMergeSort;
    private FourWayMergeSort fourWayMergeSort;
    private IntGenerator generator;
    private boolean asc;

    public SortingCorrectnessNaturalThreeFourWayTest(boolean asc) {
        this.asc = asc;
    }

    @Before
    public void setUp() {
        topDownMergeSort = new BinaryMergeSort();
        naturalMergeSort = new NaturalMergeSort();
        threeWayMergeSort = new ThreeWayMergeSort();
        fourWayMergeSort = new FourWayMergeSort();
        generator = new IntGeneratorImpl();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {true}, {false}
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
        int[] threeWayResult = new int[num]; System.arraycopy(data, 0, threeWayResult, 0, num);
        int[] fourWayResult = new int[num]; System.arraycopy(data, 0, fourWayResult, 0, num);

        // When
        topDownMergeSort.setAscending(asc); topDownMergeSort.sort(topDownResult);
        naturalMergeSort.setAscending(asc); naturalMergeSort.sort(naturalResult);
        threeWayMergeSort.setAscending(asc); threeWayMergeSort.sort(threeWayResult);
        fourWayMergeSort.setAscending(asc); fourWayMergeSort.sort(fourWayResult);

        // Then
        assertArrayEquals(topDownResult, naturalResult);
        assertArrayEquals(topDownResult, threeWayResult);
        assertArrayEquals(topDownResult, fourWayResult);
    }
}
