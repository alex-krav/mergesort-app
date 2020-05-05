package com.alexoft.service;

import com.alexoft.sorting.MergeSort;
import com.alexoft.sorting.TopDownImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test class for GeneratorServiceImpl.java
 */
public class GeneratorServiceImplTest {

    private GeneratorService generator;
    private MergeSort mergeSort;

    @Before
    public void setUp() {
        mergeSort = new TopDownImpl();
    }

    @Test
    public void generate() {
        // Given
        int number = 10;
        generator = new GeneratorServiceImpl();
        // When
        int[] array1 = generator.generate(number);
        int[] array2 = generator.generate(number);
        // Then
        assertEquals(number, array1.length);
        assertEquals(number, array2.length);
        assertFalse(Arrays.equals(array1, array2));
    }

    @Test
    public void generateMinMax() {
        // Given
        int number = 10, min = 1, max = 50;
        generator = new GeneratorServiceImpl(min, max);
        // When
        int[] array1 = generator.generate(number);
        int[] array2 = generator.generate(number);
        mergeSort.sort(array1);
        mergeSort.sort(array2);

        // Then
        assertEquals(number, array1.length);
        assertEquals(number, array2.length);
        assertFalse(Arrays.equals(array1, array2));

        assertTrue(array1[0] >= 1);
        assertTrue(array1[number-1] <= 50);
        assertTrue(array2[0] >= 1);
        assertTrue(array2[number-1] <= 50);
    }
}