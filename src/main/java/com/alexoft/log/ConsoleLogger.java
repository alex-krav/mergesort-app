package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Class for logging interim and output results,
 * as well as algorithms statistics
 */
public class ConsoleLogger implements Logger {
    private Writer console = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(String text, int[] numbers) {
        print(text);
        print(numbers);
    }

    /**
     * Logs single message
     * @param text message string
     */
    public void print(String text) {
        try {
            console.write("\n");
            console.write(text);
            console.write("\n");
            console.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs message and array of numbers
     * @param numbers integers array
     */
    public void print(int[] numbers) {
        try {
            console.write("[ ");
            for(int i = 0; i < numbers.length; ++i) {
                console.write(numbers[i]+"");
                if (i < numbers.length - 1)
                    console.write(", ");
            }
            console.write(" ]\n");
            console.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs statistics of an algorithm, after
     * method had finished sorting of array
     * @param stats statistics object
     */
    public void print(AlgoStats stats) {
        try {
            console.write("\n");
            console.write(String.format("%s statistics\n", stats.getAlgoName()));
            console.write(String.format("elements: %d\n", stats.getArraySize()));
            console.write(String.format("copies: %d\n", stats.getCopies()));
            console.write(String.format("splits: %d\n", stats.getSplits()));
            console.write(String.format("merges: %d\n", stats.getMerges()));
            // TODO: calc O notation from N elements ?
            console.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWriter(Writer console) {
        this.console = console;
    }
}
