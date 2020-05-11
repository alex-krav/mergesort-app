package com.alexoft.parser;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Interface for reading input data from different sources.
 */
public interface Parser {

    /**
     * Reads array of integers from file
     * @param file input file
     * @return array of integers
     * @throws FileNotFoundException if file is not found
     */
    int[] readFile(File file) throws FileNotFoundException;

    /**
     * Reads array of integers from string
     * @param str input string
     * @return array of integers
     */
    int[] readString(String str);
}
