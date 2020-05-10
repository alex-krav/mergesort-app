package com.alexoft.sorting;

import com.alexoft.parser.Parser;
import com.alexoft.parser.ParserImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Test class for IOServiceImpl.java
 */
public class ParserImplTest {

    private Parser parser;
    private int[] data = {0,1,2,3,4,5,6,7,8,9};

    @Before
    public void setUp() {
        parser = new ParserImpl();
    }

    @Test
    public void readFile() throws URISyntaxException, FileNotFoundException {
        // Given
        File file = getFile("input_data.txt");
        // When
        int[] numbers = parser.readFile(file);
        // Then
        assertArrayEquals(data, numbers);
    }

    /**
     * Gets file object for filename
     * @param filename file name
     * @return file object
     * @throws URISyntaxException if location of codesource
     * cannot be converted to URI
     */
    private File getFile(String filename) throws URISyntaxException {
        return Paths.get(
                ParserImplTest.class.getProtectionDomain().getCodeSource().getLocation().toURI()
        ).resolve(
                Paths.get(filename)
        ).toFile();
    }
}