package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.Scanner;

/**
 * Implementation that logs to standard output stream via terminal
 */
public class TerminalLogger implements Logger {
    // number of logged interim arrays while algorithm work
    public static int MAX_INTERIM_RESULTS = 50;
    // output stream for logging
    private Writer out = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * Logs single message. Adds line-breaks before and after message
     * @param text message string
     */
    @Override
    public void print(String text) {
        try {
            out.write("\n" + text + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print(String text, File file) {
        try(Scanner s = new Scanner(file)) {
            int size = s.nextInt();
            out.write("\n"+text+"\n");
            out.write("[ ");
            if (size > 10) {
                for(int i = 0; i < 5; ++i) {
                    out.write(s.nextInt()+"");
                    if (i < 4)
                        out.write(", ");
                }
                out.write(" ... ");
                for(int i = 0; i < size - 10; ++i)
                    s.nextInt();
                for(int i = 0; i < 5; ++i) {
                    out.write(s.nextInt()+"");
                    if (i < 4)
                        out.write(", ");
                }
            } else
                for(int i = 0; i < size; ++i) {
                    out.write(s.nextInt()+"");
                    if (i < size - 1)
                        out.write(", ");
                }
            out.write(" ]\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print(String text, File file, int size) {
        try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            out.write("\n"+text+"\n");
            out.write("[ ");
            if (size > 10) {
                for(int i = 0; i < 5; ++i) {
                    out.write(in.readInt()+"");
                    if (i < 4)
                        out.write(", ");
                }
                out.write(" ... ");
                for(int i = 0; i < size - 10; ++i)
                    in.readInt();
                for(int i = 0; i < 5; ++i) {
                    out.write(in.readInt()+"");
                    if (i < 4)
                        out.write(", ");
                }
            } else
                for(int i = 0; i < size; ++i) {
                    out.write(in.readInt()+"");
                    if (i < size - 1)
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
    @Override
    public void print(AlgoStats stats) {
        try {
            out.write("\n");
            out.write(String.format("%s statistics\n", stats.getAlgoName()));
            out.write(String.format("splits: %d\n", stats.getSplits()));
            out.write(String.format("merges: %d\n", stats.getMerges()));
            out.write(String.format("exceptions: %d\n", stats.getExceptions()));
            if (stats.getTimeNanoSeconds()/1_000_000 == 0)
                out.write(String.format("time: %d ns\n", stats.getTimeNanoSeconds()));
            else
                out.write(String.format("time: %d ms\n", stats.getTimeNanoSeconds()/1_000_000));
            // TODO: calc O notation from N elements ?
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
