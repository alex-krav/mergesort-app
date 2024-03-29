package com.alexoft.log;

import com.alexoft.algo.AlgoStats;
import com.alexoft.ui.View;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation for logging to application logging panel.
 */
public class ScreenLogger implements Logger {
    // logging panel (JPanel of Swing framework)
    private final View.LogPane screen;

    public ScreenLogger(View.LogPane screen) {
        this.screen = screen;
    }

    @Override
    public void print(String text, File file) {
        JTextArea textArea = screen.getTextArea();
        invoke(() -> {
            textArea.append(text+"\n[ ");
            try (Scanner s = new Scanner(file)) {
                int size = s.nextInt();
                // print all numbers from file, split with comma
                for (int i = 0; i < size; ++i) {
                    textArea.append(s.nextInt() + "");
                    if (i < size-1)
                        textArea.append(", ");
                }
                textArea.append(" ]\n");
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void print(AlgoStats stats) {
        JTextArea textArea = screen.getTextArea();
        invoke(() -> {
            textArea.append("\n");
            textArea.append(String.format("%s statistics\n", stats.getAlgoName()));
            textArea.append(String.format("size: %d\n", stats.getArraySize()));
            textArea.append(String.format("splits: %d\n", stats.getSplits()));
            textArea.append(String.format("merges: %d\n", stats.getMerges()));
            textArea.append("complexity: " + formatter.format(stats.countComplexity()) + "\n");
            // write running time in ns, ms or seconds
            if (stats.getTimeNanoSeconds()/1_000_000 == 0)
                textArea.append(String.format("time: %d ns\n", stats.getTimeNanoSeconds()));
            else if (stats.getTimeNanoSeconds()/1_000_000_000 == 0)
                textArea.append(String.format("time: %d ms\n", stats.getTimeNanoSeconds()/1_000_000));
            else
                textArea.append(String.format("time: %d s\n", stats.getTimeNanoSeconds()/1_000_000_000));
        });
    }

    @Override
    public void print(String text) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void print(String text, File file, int size) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void print(String text, List<Integer> list, int size) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Swing framework events model accepts sending events
     * to its Event queue
     * @param runnable object with event data
     */
    private void invoke(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
}
