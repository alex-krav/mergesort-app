package com.alexoft.log;

import com.alexoft.algo.AlgoStats;
import com.alexoft.ui.View;

import javax.swing.*;

public class ScreenLogger implements Logger {
    private View.LogPane screen;

    public ScreenLogger(View.LogPane screen) {
        this.screen = screen;
    }

    public void print(String text, int[] numbers) {
        JTextArea textArea = screen.getTextArea();
        invoke(() -> {
            textArea.append(text+"\n");
            textArea.append("[ ");
            for(int i = 0; i < numbers.length; ++i) {
                textArea.append(numbers[i]+"");
                if (i < numbers.length - 1)
                    textArea.append(", ");
            }
            textArea.append(" ]\n");
        });
    }

    public void print(AlgoStats stats) {
        JTextArea textArea = screen.getTextArea();
        invoke(() -> {
            textArea.append("\n");
            textArea.append(String.format("%s statistics\n", stats.getAlgoName()));
            textArea.append(String.format("elements: %d\n", stats.getArraySize()));
            textArea.append(String.format("copies: %d\n", stats.getCopies()));
            textArea.append(String.format("splits: %d\n", stats.getSplits()));
            textArea.append(String.format("merges: %d\n", stats.getMerges()));
            // TODO: calc O notation from N elements ?
        });
    }

    @Override
    public void print(String text) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void print(int[] numbers) {
        throw new RuntimeException("Not implemented!");
    }

    public void setScreen(View.LogPane screen) {
        this.screen = screen;
    }

    private void invoke(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
}
