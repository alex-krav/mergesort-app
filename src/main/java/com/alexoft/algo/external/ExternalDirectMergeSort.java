package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ExternalDirectMergeSort extends ExternalMergeSortBase {
    private Comparator<HeapNode> comparator;

    @Override
    public void sort(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file provided!");
        }

        long startTime = System.nanoTime();
        log(String.format("Direct merge sort is starting %s...", getAscString()));
        comparator = (isAscending())
                ? Comparator.comparingInt(x -> x.val)
                : Comparator.<HeapNode>comparingInt(x -> x.val).reversed();
        algoStats = new AlgoStats("Direct merge sort");
        directSort(file);
        log("Direct merge sort output", file);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    private void directSort(File input) {
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

    private void fileMerge(File aFile, File bFile, File cFile, int width, int size) {
        try(DataOutputStream aOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aFile)));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            int[] inI = {0, 0};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            while (i < size) {
                try {
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i; ++inI[0];
                } catch (IOException e) {
                    inI[0] = width; algoStats.addException();
                }
                if (i < size)
                    try {
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i; ++inI[1];
                    } catch (IOException e) {
                        inI[1] = width; algoStats.addException();
                    }

                while (!pq.isEmpty()) {
                    HeapNode root = pq.remove();
                    aOut.writeInt(root.val);

                    if (i < size && inI[root.id] < width) {
                        try {
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id]; algoStats.addException();
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

    private void fileMergeOutput(File aFile, File bFile, File cFile, int width, int size) {
        try(BufferedWriter aOut = new BufferedWriter(new FileWriter(aFile));
        DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
        DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            int[] inI = {0, 0};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            aOut.write(size + "\n");
            while (i < size) {
                try {
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i; ++inI[0];
                } catch (IOException e) {
                    inI[0] = width; algoStats.addException();
                }
                if (i < size)
                    try {
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i; ++inI[1];
                    } catch (IOException e) {
                        inI[1] = width; algoStats.addException();
                    }

                while (!pq.isEmpty()) {
                    HeapNode root = pq.remove();
                    aOut.write(root.val + " ");

                    if (i < size && inI[root.id] < width) {
                        try {
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id]; algoStats.addException();
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

    private void fileSplit(File aFile, File bFile, File cFile, int width, int size) {
        try(DataInputStream aIn = new DataInputStream(new BufferedInputStream(new FileInputStream(aFile)));
            DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bFile)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cFile)))) {

            int i = 0;
            while (i < size) {
                for (int j = 0; j < width; ++j) {
                    bOut.writeInt(aIn.readInt()); ++i;
                    if (i == size) break;
                }
                if (i == size) break;
                for (int j = 0; j < width; ++j) {
                    cOut.writeInt(aIn.readInt()); ++i;
                    if (i == size) break;
                }
            }
            bOut.flush(); cOut.flush();
            algoStats.addSplits();
            logInterim("Direct merge sort interim result", aFile, size);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
