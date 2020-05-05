package com.alexoft.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.alexoft.TestUtils.getFile;
import static org.junit.Assert.*;

/**
 * Test class for IOServiceImpl.java
 */
public class IOServiceImplTest {

    private IOService ioService;
    private int[] data = {0,1,2,3,4,5,6,7,8,9};

    @Before
    public void setUp() {
        ioService = new IOServiceImpl();
    }

    @Test
    public void readFile() throws URISyntaxException, FileNotFoundException {
        // Given
        File file = getFile("input_data.txt");
        // When
        int[] numbers = ioService.readFile(file);
        // Then
        assertArrayEquals(data, numbers);
    }

    @Test
    public void writeFileWithName() throws IOException {
        ioService.writeFile("output_data.txt", data);
    }

    @Test
    public void writeFileWithoutName() throws IOException {
        ioService.writeFile(data);
    }
}