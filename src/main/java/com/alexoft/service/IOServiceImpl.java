package com.alexoft.service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Class for reading from and writing arrays to files.
 * Input array must be of format: first line - number of items,
 * second line - space separated integer numbers
 */
public class IOServiceImpl implements IOService {

    /**
     * Reads array of integers from file
     * @param file file object
     * @return array of integers
     * @throws FileNotFoundException if file is not found
     */
    @Override
    public int[] readFile(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        int[] array = new int[s.nextInt()];
        for (int i = 0; i < array.length; i++)
            array[i] = s.nextInt();
        return array;
    }

    /**
     * Writes integers array to file, first line is array length,
     * second line is space separated integer numbers
     * @param fileName output filename
     * @param numbers integers array
     * @throws IOException if output file cannot be created
     */
    @Override
    public void writeFile(String fileName, int[] numbers) throws IOException {
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(fileName));
        int n = numbers.length;

        outputWriter.write(n+"");
        outputWriter.newLine();
        for (int number : numbers)
            outputWriter.write(number + " ");

        outputWriter.flush();
        outputWriter.close();
    }

    /**
     * Writes integers array to a file, which name is generated
     * using datetime pattern, like output_2020-05-05_21-13-10.txt
     * @param numbers integers array
     * @throws IOException if output file can't be created
     */
    @Override
    public void writeFile(int[] numbers) throws IOException {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fileName = String.format("output_%s.txt",
                timeStampPattern.format(java.time.LocalDateTime.now()));
        writeFile(fileName, numbers);
    }
}
