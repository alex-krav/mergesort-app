package com.alexoft.parser;

import java.io.*;

/**
 * Class for reading from and writing arrays to files.
 * Input array must be of format: first line - number of items,
 * second line - space separated integer numbers
 */
public class ParserImpl implements Parser {

    /**
     * Reads array of integers from file
     * @param file file object
     * @return array of integers
     * @throws FileNotFoundException if file is not found
     */
    @Override
    public int[] readFile(File file) throws FileNotFoundException {
        java.util.Scanner s = new java.util.Scanner(file);
        int[] array = new int[s.nextInt()];
        for (int i = 0; i < array.length; i++)
            array[i] = s.nextInt();
        return array;
    }

    @Override
    public int[] readString(String str) {
        java.util.Scanner s = new java.util.Scanner(str);
        int[] array = new int[s.nextInt()];
        for (int i = 0; i < array.length; i++)
            array[i] = s.nextInt();
        return array;
    }
}
