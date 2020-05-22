package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.*;

/**
 * Implementation of external merge sort.
 * Uses three working files, together with input file - four:
 * one for input, two for split/merge, one for output.
 * A natural merge sort is similar to a direct merge sort
 * except that any naturally occurring runs (sorted sequences)
 * in the input are exploited.
 */
public class NaturalMergeSort extends ExternalMergeSortBase {
    private Comparator<HeapNode> comparator;
    private int size;

    @Override
    public void sort(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file provided!");
        }

        long startTime = System.nanoTime();
        log(String.format("Natural merge sort is starting %s...", getAscString()));
        comparator = (isAscending())
                ? Comparator.comparingInt(x -> x.val)
                : Comparator.<HeapNode>comparingInt(x -> x.val).reversed();
        algoStats = new AlgoStats("Natural merge sort");
        initInterimResultCounters();
        naturalDirectSort(file);
        log("Natural merge sort output", file);
        algoStats.setTimeNanoSeconds(System.nanoTime() - startTime);
    }

    /**
     * Common method with sort cycle and create/delete files operations.
     * @param aTmp file with integers
     */
    private void naturalDirectSort(File aTmp) {
        // create three working files
        File bTmp = new File("b.tmp");
        File cTmp = new File("c.tmp");
        File dTmp = new File("d.tmp");

        boolean first = true, init = true, done;
        // split-merge cycle: split a to b,c -> then merge b,c to d
        while(true)
        {
            if (first) {
                done = fileSplitMergeSave(aTmp, bTmp, cTmp, dTmp, init); init = false;
            } else {
                done = fileSplitMergeSave(dTmp, bTmp, cTmp, aTmp, false);
            }
            if (done) break;
            first = !first;
        }

        if (first && this.size >= 2) {
            // save name and delelte original file
            File originalFileName = new File(aTmp.getParent(), aTmp.getName());
            if (!aTmp.delete())
                log("Couldn't delete original file.");
            // rename D file to original name
            if (!dTmp.renameTo(originalFileName))
                log("Couldn't rename d.tmp to original file.");
            // delete B and C files
            if (!cTmp.delete() || !bTmp.delete())
                log("Couldn't delete two working tmp files.");
        } else {
            // delete three working files
            if (!bTmp.delete() || !cTmp.delete() || !dTmp.delete())
                log("Couldn't delete three working tmp files.");
        }
    }

    /**
     * Compound counter object to be used between several cycles
     */
    private static class Counter {
        public int i = 0; // total numbers read
        public int read = 0; // numbers read during one split cycle
        public Integer idle; // hanging number not included in first output file
        public Integer prev; // previous number read
        public boolean append; // append number to output file or clear before writing
    }

    /**
     * Main method with split-merge cycle.
     * @param aTmp input file
     * @param bTmp first working file for split
     * @param cTmp second working file for split
     * @param dTmp output file for merge
     * @param init flag saying whether it's a first split cycle
     * @return flag saying if we're done with sorting
     */
    private boolean fileSplitMergeSave(File aTmp, File bTmp, File cTmp, File dTmp, boolean init) {
        boolean done = false;
        if (init) { // read input file
            try(Scanner aIn = new Scanner(aTmp)) {
                this.size = aIn.nextInt(); // read input array size
                if (this.size < 2) return true;
                algoStats.setArraySize(this.size);
                Counter counter = new Counter();
                while (counter.i < this.size || counter.idle != null) {
                    // split A to B,C + count READ
                    fileSplitNatural(aIn, bTmp, cTmp, counter);
                    // merge B,C to D
                    if (counter.read < this.size) // interim merge method
                        fileMerge(dTmp, bTmp, cTmp, counter);
                    else { // final merge method
                        fileMergeOutput(dTmp, bTmp, cTmp, counter);
                        done = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (DataInputStream aIn = new DataInputStream(new BufferedInputStream(new FileInputStream(aTmp)))) {
                Counter counter = new Counter();
                while (counter.i < this.size || counter.idle != null) {
                    // split A to B,C + count READ
                    fileSplitNatural(aIn, bTmp, cTmp, counter);
                    // merge B,C to D
                    if (counter.read < this.size) // interim merge method
                        fileMerge(dTmp, bTmp, cTmp, counter);
                    else { // final merge method
                        fileMergeOutput(dTmp, bTmp, cTmp, counter);
                        done = true;
                    }
                }
                // log interim result
                if (!done) logInterim("Natural merge sort interim result", dTmp, this.size);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return done;
    }

    /**
     * Split method used once for initial read of input file.
     * @param aIn input file
     * @param bTmp first output file
     * @param cTmp second output file
     * @param counter compound counter
     */
    private void fileSplitNatural(Scanner aIn, File bTmp, File cTmp, Counter counter) {
        try(DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bTmp)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cTmp)))) {

            boolean first = true; int current; counter.read = 0;
            // until all numbers from input file are read
            while(counter.i < this.size || counter.idle != null) {
                if (first) { // write to first file
                    if (null == counter.prev) { // initial read
                        counter.prev = current = aIn.nextInt(); ++counter.i; ++counter.read;
                        bOut.writeInt(current);
                    } else {
                        // write idle number if exists
                        if (counter.idle != null) { bOut.writeInt(counter.idle); counter.idle = null; ++counter.read; }
                        if (counter.i < this.size) {
                            // read number from input file
                            current = aIn.nextInt(); ++counter.i; ++counter.read;
                            if (isAscending()) { // if ascending sort order
                                if (current < counter.prev == isAscending()) {
                                    // first number of next run
                                    counter.idle = counter.prev = current;
                                    first = false;
                                } else {
                                    // write number to output file
                                    counter.prev = current;
                                    bOut.writeInt(current);
                                }
                            } else { // if descending order
                                if (current <= counter.prev == isAscending()) {
                                    // first number of next run
                                    counter.idle = counter.prev = current;
                                    first = false;
                                } else {
                                    // write number to output file
                                    counter.prev = current;
                                    bOut.writeInt(current);
                                }
                            }
                        }
                    }
                }
                if (!first) { // write to second file
                    // write idle number if exists
                    if (counter.idle != null) { cOut.writeInt(counter.idle); counter.idle = null; }
                    if (counter.i < this.size) {
                        // read number from input file
                        current = aIn.nextInt(); ++counter.i; ++counter.read;
                        if (isAscending()) { // if ascending sort order
                            if (current < counter.prev == isAscending()) {
                                // first number of next run
                                counter.idle = counter.prev = current;
                                --counter.read;
                                break;
                            } else {
                                // write number to output file
                                counter.prev = current;
                                cOut.writeInt(current);
                            }
                        } else { // if descending order
                            if (current <= counter.prev == isAscending()) {
                                // first number of next run
                                counter.idle = counter.prev = current;
                                --counter.read;
                                break;
                            } else {
                                // write number to output file
                                counter.prev = current;
                                cOut.writeInt(current);
                            }
                        }
                    }
                }
            }
            algoStats.addSplits();
            bOut.flush();
            cOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interim split method used for second and further splits of input file.
     * @param aIn input file
     * @param bTmp first output file
     * @param cTmp second output file
     * @param counter compound counter
     */
    private void fileSplitNatural(DataInputStream aIn, File bTmp, File cTmp, Counter counter) {
        try(DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bTmp)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cTmp)))) {

            boolean first = true; int current; counter.read = 0;
            // until all numbers from input file are read
            while(counter.i < this.size || counter.idle != null) {
                if (first) { // write to first file
                    if (null == counter.prev) { // initial read
                        counter.prev = current = aIn.readInt(); ++counter.i; ++counter.read;
                        bOut.writeInt(current);
                    } else {
                        // write idle number if exists
                        if (counter.idle != null) { bOut.writeInt(counter.idle); counter.idle = null; ++counter.read; }
                        if (counter.i < this.size) {
                            // read number from input file
                            current = aIn.readInt(); ++counter.i; ++counter.read;
                            if (isAscending()) { // if ascending sort order
                                if (current < counter.prev == isAscending()) {
                                    // first number of next run
                                    counter.idle = counter.prev = current;
                                    first = false;
                                } else {
                                    counter.prev = current;
                                    bOut.writeInt(current);
                                }
                            } else { // if descending sort order
                                if (current <= counter.prev == isAscending()) {
                                    counter.idle = counter.prev = current;
                                    first = false;
                                } else {
                                    counter.prev = current;
                                    bOut.writeInt(current);
                                }
                            }
                        }
                    }
                }
                if (!first) { // write to second file
                    // write idle number if exists
                    if (counter.idle != null) { cOut.writeInt(counter.idle); counter.idle = null; }
                    if (counter.i < this.size) {
                        current = aIn.readInt(); ++counter.i; ++counter.read;
                        if (isAscending()) { // if ascending sort order
                            if (current < counter.prev == isAscending()) {
                                // first number of next run
                                counter.idle = counter.prev = current;
                                --counter.read;
                                break;
                            } else {
                                // write number to output file
                                counter.prev = current;
                                cOut.writeInt(current);
                            }
                        } else { // if descending sort order
                            if (current <= counter.prev == isAscending()) {
                                // first number of next run
                                counter.idle = counter.prev = current;
                                --counter.read;
                                break;
                            } else {
                                // write number to output file
                                counter.prev = current;
                                cOut.writeInt(current);
                            }
                        }
                    }
                }
            }
            algoStats.addSplits();
            bOut.flush();
            cOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interim merge method. Merges arrays of integers from two working files to third file.
     * @param aFile file to be written to
     * @param bFile first input file
     * @param cFile second input file
     * @param counter compound counter
     */
    private void fileMerge(File aFile, File bFile, File cFile, Counter counter) {
        boolean append = counter.append; counter.append = true;
        try(DataOutputStream aOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aFile, append)));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            boolean[] has = {true, true};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            // until all numbers from input files are read
            while (i < counter.read) {
                try { // init priority queue with value from first input file
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i;
                } catch (IOException e) {
                    has[0] = false;
                }
                if (i < counter.read)
                    try { // init priority queue with value from second input file
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i;
                    } catch (IOException e) {
                        has[1] = false;
                    }

                while (!pq.isEmpty()) {
                    // get min/max number depending on sorting order
                    HeapNode root = pq.remove();
                    aOut.writeInt(root.val);

                    if (i < counter.read && has[root.id]) {
                        try { // get number from same input stream as previous number
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i;
                        } catch (IOException e) {
                            has[root.id] = false;
                        }
                    }
                }
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
     * @param counter compound counter
     */
    private void fileMergeOutput(File aFile, File bFile, File cFile, Counter counter) {
        try(BufferedWriter aOut = new BufferedWriter(new FileWriter(aFile));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            boolean[] has = {true, true};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            aOut.write(this.size + "\n"); // write input array size
            // until all numbers are read from input files
            while (i < counter.read) {
                try { // init priority queue with value from first input file
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i;
                } catch (IOException e) {
                    has[0] = false;
                }
                if (i < counter.read)
                    try { // init priority queue with value from second input file
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i;
                    } catch (IOException e) {
                        has[1] = false;
                    }

                while (!pq.isEmpty()) {
                    // get min/max number depending on sorting order
                    HeapNode root = pq.remove();
                    aOut.write(root.val + " ");

                    if (i < counter.read && has[root.id]) {
                        try { // get number from same input stream as previous number
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i;
                        } catch (IOException e) {
                            has[root.id] = false;
                        }
                    }
                }
            }
            aOut.flush();
            algoStats.addMerges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
