package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Implementation that logs to file. It accepts a file name
 * instead of text message for its text param
 */
public class FileLogger implements Logger {

    /**
     * Writes integers array to file, which name is generated.
     * First line printed is array length,
     * second line is space separated integer numbers.
     * Format that allows to accept file for program input.
     * @param numbers integers array
     */
    public void print(int[] numbers) {
        String fileName = generateFilename();
        print(fileName, numbers);
    }

    /**
     * Writes integers array to file. Main method of class
     * @param fileName output file name
     * @param numbers integers array
     */
    public void print(String fileName, int[] numbers) {
        if (null == fileName)
            fileName = generateFilename();
        try {
            BufferedWriter file = new BufferedWriter(new FileWriter(fileName));
            int n = numbers.length;

            file.write(n + "");
            file.newLine();
            for (int number : numbers)
                file.write(number + " ");

            file.flush();
            file.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Does not use this method, as it has file name only as param.
     * @param fileName file name string
     */
    @Override
    public void print(String fileName) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Does not write algorithm statistics to file
     * @param stats statistics object
     */
    @Override
    public void print(AlgoStats stats) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Generates file name using datetime pattern, like output_2020-05-05_21-13-10.txt
     * @return file name string
     */
    private String generateFilename() {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return String.format("output_%s.txt",
                timeStampPattern.format(java.time.LocalDateTime.now()));
    }
}
