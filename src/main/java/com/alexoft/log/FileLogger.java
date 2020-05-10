package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FileLogger implements Logger {

    /**
     * Writes integers array to a file, which name is generated
     * using datetime pattern, like output_2020-05-05_21-13-10.txt
     * @param numbers integers array
     */
    public void print(int[] numbers) {
        String fileName = generateFilename();
        print(fileName, numbers);
    }

    /**
     * Writes integers array to file, first line is array length,
     * second line is space separated integer numbers
     * @param fileName output filename
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

    @Override
    public void print(String text) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void print(AlgoStats stats) {
        throw new RuntimeException("Not implemented!");
    }

    private String generateFilename() {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return String.format("output_%s.txt",
                timeStampPattern.format(java.time.LocalDateTime.now()));
    }
}
