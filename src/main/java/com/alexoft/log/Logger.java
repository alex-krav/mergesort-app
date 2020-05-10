package com.alexoft.log;

import com.alexoft.algo.AlgoStats;

public interface Logger {
    void print(String text, int[] numbers);
    void print(String text);
    void print(int[] numbers);
    void print(AlgoStats stats);
}
