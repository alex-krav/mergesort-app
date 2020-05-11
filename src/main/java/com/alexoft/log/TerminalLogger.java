package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Implementation that logs to standard output stream via terminal
 */
public class TerminalLogger implements Logger {
    // number of logged interim arrays while algorithm work
    public static int MAX_INTERIM_RESULTS = 50;
    // output stream for logging
    private Writer out = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(String text, int[] numbers) {
        print(text);
        print(numbers);
    }

    /**
     * Logs single message. Adds line-breaks before and after message
     * @param text message string
     */
    public void print(String text) {
        try {
            out.write("\n");
            out.write(text);
            out.write("\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs message and array of numbers. If array lengths is more than 10,
     * then displays first and last 5 elements of array
     * @param numbers integers array
     */
    public void print(int[] numbers) {
        int n = numbers.length;
        try {
            out.write("[ ");
            if (n > 10) {
                for(int i = 0; i < 5; ++i) {
                    out.write(numbers[i]+"");
                    if (i < 4)
                        out.write(", ");
                }
                out.write(" ... ");
                for(int i = n-5; i < n; ++i) {
                    out.write(numbers[i]+"");
                    if (i < n - 1)
                        out.write(", ");
                }
            } else
                for(int i = 0; i < n; ++i) {
                    out.write(numbers[i]+"");
                    if (i < n - 1)
                        out.write(", ");
                }
            out.write(" ]\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs statistics of an algorithm: array length, number of copies,
     * splits and merges
     * @param stats statistics object
     */
    public void print(AlgoStats stats) {
        try {
            out.write("\n");
            out.write(String.format("%s statistics\n", stats.getAlgoName()));
            out.write(String.format("elements: %d\n", stats.getArraySize()));
            out.write(String.format("copies: %d\n", stats.getCopies()));
            out.write(String.format("splits: %d\n", stats.getSplits()));
            out.write(String.format("merges: %d\n", stats.getMerges()));
            // TODO: calc O notation from N elements ?
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
