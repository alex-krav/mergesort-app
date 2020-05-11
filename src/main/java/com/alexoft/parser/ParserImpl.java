package com.alexoft.parser;

import java.io.*;

/**
 * Input data must be of format: first number - array length,
 * following numbers - space separated integer elements of array
 */
public class ParserImpl implements Parser {

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
