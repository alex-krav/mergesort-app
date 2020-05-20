package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.*;

public class Balanced2wayMergeSort extends ExternalMergeSortBase {
    private Comparator<HeapNode> comparator;

    @Override
    public void sort(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file provided!");
        }

        long startTime = System.nanoTime();
        log(String.format("Balanced 2-way merge sort is starting %s...", getAscString()));
        comparator = (isAscending())
                ? Comparator.comparingInt(x -> x.val)
                : Comparator.<HeapNode>comparingInt(x -> x.val).reversed();
        algoStats = new AlgoStats("Balanced 2-way merge sort");
        balanced2waySort(file);
        log("Balanced 2-way merge sort output", file);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    private void balanced2waySort(File aTmp) {
        int size = 0;
        // create three working files
        File bTmp = new File("b.tmp");
        File cTmp = new File("c.tmp");
        File dTmp = new File("d.tmp");

        try(Scanner s = new Scanner(aTmp);
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
        // merge cycle: merge b,c to a,d
        for (int width = 1; width < size; width = 2 * width) // 1, 2, 4, 8
        {
            if (first) {
                if (2 * width < size)
                    fileMerge(aTmp, dTmp, bTmp, cTmp, width, size);
                else {
                    fileMergeOutput(aTmp, dTmp, bTmp, cTmp, width, size);
                    // delete three working files
                    if (!bTmp.delete() || !cTmp.delete() || !dTmp.delete())
                        log("Couldn't delete three working tmp files.");
                }
            } else {
                if (2 * width < size)
                    fileMerge(bTmp, cTmp, aTmp, dTmp, width, size);
                else {
                    fileMergeOutput(bTmp, cTmp, aTmp, dTmp, width, size);
                    // save name and del original aTmp
                    File originalFileName = new File(aTmp.getParent(), aTmp.getName());
                    if (!aTmp.delete())
                        log("Couldn't delete original file.");
                    // rename bTmp to original
                    if (!bTmp.renameTo(originalFileName))
                        log("Couldn't rename b.tmp to original file.");
                    // del cTmp and dTmp
                    if (!cTmp.delete() || !dTmp.delete())
                        log("Couldn't delete two working tmp files.");
                }
            }
            first = !first;
        }
    }

    private void fileMerge(File aTmp, File dTmp, File bTmp, File cTmp, int width, int size) {
        try(DataOutputStream aOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aTmp)));
            DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dTmp)));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bTmp)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cTmp)))) {

            List<Integer> interimLog = new ArrayList<>(); int counter = 0;
            DataInputStream[] in = {bIn, cIn}; DataOutputStream[] out = {aOut, dOut};
            int i = 0;
            int[] inI = {0, 0}; boolean first = true;
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
                    if (first) out[0].writeInt(root.val);
                    else out[1].writeInt(root.val);
                    if (counter < 10) { interimLog.add(root.val); ++counter; }

                    if (i < size && inI[root.id] < width) {
                        try {
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id];
                        } catch (IOException e) {
                            inI[root.id] = width; algoStats.addException();
                        }
                    }
                }
                inI[0] = 0; inI[1] = 0; first = !first;
            }
            logInterim("Balanced 2-way merge sort interim result", interimLog, size);
            aOut.flush(); dOut.flush();
            algoStats.addMerges();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void fileMergeOutput(File aTmp, File dTmp, File bTmp, File cTmp, int width, int size) {
        try(BufferedWriter aOut = new BufferedWriter(new FileWriter(aTmp));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bTmp)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cTmp)))) {

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
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i; ++inI[root.id];
                        } catch (IOException e) {
                            inI[root.id] = width; algoStats.addException();
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
}
