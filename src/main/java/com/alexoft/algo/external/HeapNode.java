package com.alexoft.algo.external;

/**
 * Class for priority queue of integers
 */
public class HeapNode {
    public Integer val; // value of integer
    public int id; // id of input stream

    public HeapNode(Integer val, int id) {
        this.val = val;
        this.id = id;
    }
}
