package com.alexoft.random;

public interface IntGenerator {

    int[] generate(Integer n);
    int[] generate(Integer n, Integer min, Integer max);
}
