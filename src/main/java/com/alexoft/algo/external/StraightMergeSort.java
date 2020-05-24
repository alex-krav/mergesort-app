package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Implementation of external merge sort.
 * Uses two working files, together with input file - three:
 * one for input, two for output.
 */
public class StraightMergeSort extends ExternalMergeSortBase {
    // comparator object for priority queue
    private Comparator<HeapNode> comparator;

    @Override
    public void sort(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file provided!");
        }

        long startTime = System.nanoTime();
        log(String.format("Straight merge sort is starting %s...", getAscString()));
        comparator = (isAscending())
                ? Comparator.comparingInt(x -> x.val)
                : Comparator.<HeapNode>comparingInt(x -> x.val).reversed();
        algoStats = new AlgoStats("Straight merge sort " + getAscString());
        straightSort(file);
        log("Straight merge sort output", file);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Main method with split-merge cycle
     * @param input file with integers
     */
    private void straightSort(File input) {
        int size = 0;
        // create two working files
        File bTmp = new File("b.tmp");
        File cTmp = new File("c.tmp");

        try(Scanner s = new Scanner(input);
            DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bTmp)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cTmp)))) {

            size = s.nextInt();
            initInterimResultCounters();
            algoStats.setArraySize(size);
            // init B and C with 1 size elements
            int i = 0;
            while(true) {
                if (i < size) {
                    bOut.writeInt(s.nextInt()); ++i;
                } else break;
                if (i < size) {
                    cOut.writeInt(s.nextInt()); ++i;
                } else break;
            }
            algoStats.addSplits();
            bOut.flush();
            cOut.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }

        boolean first = true;
        // split-merge cycle: split a to b,c -> then merge b,c to a
        for (int width = 1; width < size; width = 2 * width) // 1, 2, 4, 8
        {
            if(!first) {
                fileSplit(input, bTmp, cTmp, width, size);
            }
            if (2*width < size)
                fileMerge(input, bTmp, cTmp, width, size);
            else
                fileMergeOutput(input, bTmp, cTmp, width, size);
            first = false;
        }
        // delete working files
        if (!bTmp.delete() || !cTmp.delete()) {
            log("Couldn't delete working tmp files.");
        }
    }

    /**
     * Interim merge method. Merges arrays of integers from two working files to third file.
     * @param aFile file to be written to
     * @param bFile first input file
     * @param cFile second input file
     * @param width number of integers on each working file for one merge iteration
     * @param size size of input array
     */
    private void fileMerge(File aFile, File bFile, File cFile, int width, int size) {
        try(DataOutputStream aOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aFile)));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            int[] inI = {0, 0};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            // until all numbers from input files are read
            while (i < size) {
                try { // init priority queue with value from first input file
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i; ++inI[0];
                } catch (IOException e) {
                    inI[0] = width;
                }
                if (i < size)
                    try { // init priority queue with value from second input file
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i; ++inI[1];
                    } catch (IOException e) {
                        inI[1] = width;
                    }

                while (!pq.isEmpty()) {
                    // get min/max number depending on sorting order
                    HeapNode root = pq.remove();
                    aOut.writeInt(root.val);

                    if (i < size && inI[root.id] < width) {
                        try { // get number from same input stream as previous number
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id];
                        } catch (IOException e) {
                            inI[root.id] = width;
                        }
                    }
                }
                inI[0] = 0; inI[1] = 0;
            }
            aOut.flush();
            algoStats.addMerges();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Final merge method to output file.
     * @param aFile file to be written to
     * @param bFile first input file
     * @param cFile second input file
     * @param width number of integers on each working file for one merge iteration
     * @param size size of input array
     */
    private void fileMergeOutput(File aFile, File bFile, File cFile, int width, int size) {
        try(BufferedWriter aOut = new BufferedWriter(new FileWriter(aFile));
        DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
        DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            int[] inI = {0, 0};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            aOut.write(size + "\n"); // write input array size
            while (i < size) {
                try { // init priority queue with value from first input file
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i; ++inI[0];
                } catch (IOException e) {
                    inI[0] = width;
                }
                if (i < size)
                    try { // init priority queue with value from second input file
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i; ++inI[1];
                    } catch (IOException e) {
                        inI[1] = width;
                    }

                while (!pq.isEmpty()) {
                    // get min/max number depending on sorting order
                    HeapNode root = pq.remove();
                    aOut.write(root.val + " ");

                    if (i < size && inI[root.id] < width) {
                        try { // get number from same input stream as previous number
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id];
                        } catch (IOException e) {
                            inI[root.id] = width;
                        }
                    }
                }
                inI[0] = 0; inI[1] = 0;
            }
            aOut.flush();
            algoStats.addMerges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Split method. Splits input file to two working files.
     * @param aFile input file
     * @param bFile first output working file
     * @param cFile second output working file
     * @param width number of integers for each working file for one split iteration
     * @param size size of input array
     */
    private void fileSplit(File aFile, File bFile, File cFile, int width, int size) {
        try(DataInputStream aIn = new DataInputStream(new BufferedInputStream(new FileInputStream(aFile)));
            DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bFile)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cFile)))) {

            int i = 0;
            while (i < size) {
                // write 'width' elements to first working file
                for (int j = 0; j < width; ++j) {
                    bOut.writeInt(aIn.readInt()); ++i;
                    if (i == size) break;
                }
                if (i == size) break;
                // write 'width' elements to second working file
                for (int j = 0; j < width; ++j) {
                    cOut.writeInt(aIn.readInt()); ++i;
                    if (i == size) break;
                }
            }
            bOut.flush(); cOut.flush();
            algoStats.addSplits();
            // log interim result
            logInterim("Straight merge sort interim result", aFile, size);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
