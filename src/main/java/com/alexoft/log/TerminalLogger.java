package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation that logs to standard output stream via terminal
 */
public class TerminalLogger implements Logger {
    // number of logged interim arrays while algorithm work
    public static int MAX_INTERIM_RESULTS = 50;
    // output stream for logging
    private final Writer out = new BufferedWriter(new OutputStreamWriter(System.out));

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
                // write first ten numbers
                for(int i = 0; i < 10; ++i) {
                    out.write(s.nextInt()+"");
                    if (i < 9)
                        out.write(", ");
                }
                out.write(" ... ");
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
            // write first ten numbers
            if (size > 10) {
                for(int i = 0; i < 10; ++i) {
                    out.write(in.readInt()+"");
                    if (i < 9)
                        out.write(", ");
                }
                out.write(" ... ");
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

    @Override
    public void print(String text, List<Integer> list, int size) {
        try {
            out.write("\n"+text+"\n");
            out.write("[ ");
            // write first ten numbers
            if (size > 10) {
                for(int i = 0; i < 10; ++i) {
                    out.write(list.get(i)+"");
                    if (i < 9)
                        out.write(", ");
                }
                out.write(" ... ");
            } else
                for(int i = 0; i < list.size(); ++i) {
                    out.write(list.get(i)+"");
                    if (i < list.size() - 1)
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
            out.write(String.format("size: %d\n", stats.getArraySize()));
            out.write(String.format("splits: %d\n", stats.getSplits()));
            out.write(String.format("merges: %d\n", stats.getMerges()));
            out.write("complexity: " + formatter.format(stats.countComplexity()) + "\n");
            // write time of running in ns, ms or seconds
            if (stats.getTimeNanoSeconds()/1_000_000 == 0)
                out.write(String.format("time: %d ns\n", stats.getTimeNanoSeconds()));
            else if (stats.getTimeNanoSeconds()/1_000_000_000 == 0)
                out.write(String.format("time: %d ms\n", stats.getTimeNanoSeconds()/1_000_000));
            else
                out.write(String.format("time: %d s\n", stats.getTimeNanoSeconds()/1_000_000_000));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
