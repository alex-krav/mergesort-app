package com.alexoft.algo.external;

import com.alexoft.algo.AlgoStats;

import java.io.*;
import java.util.*;

public class NaturalDirectMergeSort extends ExternalMergeSortBase {
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

    private void naturalDirectSort(File aTmp) {
        // create two working files
        File bTmp = new File("b.tmp");
        File cTmp = new File("c.tmp");
        File dTmp = new File("d.tmp");

        boolean first = true, init = true, done;
        // split-merge cycle: split a to b,c -> then merge b,c to a
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
            // save name and del original aTmp
            File originalFileName = new File(aTmp.getParent(), aTmp.getName());
            if (!aTmp.delete())
                log("Couldn't delete original file.");
            // rename dTmp to original
            if (!dTmp.renameTo(originalFileName))
                log("Couldn't rename d.tmp to original file.");
            // del bTmp and cTmp
            if (!cTmp.delete() || !bTmp.delete())
                log("Couldn't delete two working tmp files.");
        } else {
            if (!bTmp.delete() || !cTmp.delete() || !dTmp.delete())
                log("Couldn't delete three working tmp files.");
        }
    }

    private static class Counter {
        public int i = 0; //total read
        public int read = 0; //read in one split
        public Integer idle;
        public Integer prev;
        public boolean append;
    }

    private boolean fileSplitMergeSave(File aTmp, File bTmp, File cTmp, File dTmp, boolean init) {
        boolean done = false;
        if (init) {
            try(Scanner aIn = new Scanner(aTmp)) {
                this.size = aIn.nextInt();
                if (this.size < 2) return true;
                algoStats.setArraySize(this.size);
                Counter counter = new Counter();
                while (counter.i < this.size || counter.idle != null) {
                    // split to B,C + count READ
                    fileSplitNatural(aIn, bTmp, cTmp, counter);
                    // merge B,C to D
                    if (counter.read < this.size)
                        fileMerge(dTmp, bTmp, cTmp, counter);
                    else {
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
                    // split to B,C + count READ
                    fileSplitNatural(aIn, bTmp, cTmp, counter);
                    // merge B,C to D
                    if (counter.read < this.size)
                        fileMerge(dTmp, bTmp, cTmp, counter);
                    else {
                        fileMergeOutput(dTmp, bTmp, cTmp, counter);
                        done = true;
                    }
                }
                if (!done) logInterim("Natural merge sort interim result", dTmp, this.size);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return done;
    }

    private void fileSplitNatural(Scanner aIn, File bTmp, File cTmp, Counter counter) {
        try(DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bTmp)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cTmp)))) {

            boolean first = true; int current; counter.read = 0;
            while(counter.i < this.size || counter.idle != null) {
                if (first) {
                    if (null == counter.prev) {
                        counter.prev = current = aIn.nextInt(); ++counter.i; ++counter.read;
                        bOut.writeInt(current);
                    } else {
                        if (counter.idle != null) { bOut.writeInt(counter.idle); counter.idle = null; ++counter.read; }
                        if (counter.i < this.size) {
                            current = aIn.nextInt(); ++counter.i; ++counter.read;
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
                if (!first) {
                    if (counter.idle != null) { cOut.writeInt(counter.idle); counter.idle = null; }
                    if (counter.i < this.size) {
                        current = aIn.nextInt(); ++counter.i; ++counter.read;
                        if (current <= counter.prev == isAscending()) {
                            counter.idle = counter.prev = current;
                            --counter.read;
                            break;
                        } else {
                            counter.prev = current;
                            cOut.writeInt(current);
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

    private void fileSplitNatural(DataInputStream aIn, File bTmp, File cTmp, Counter counter) {
        try(DataOutputStream bOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bTmp)));
            DataOutputStream cOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cTmp)))) {

            boolean first = true; int current; counter.read = 0;
            while(counter.i < this.size || counter.idle != null) {
                if (first) {
                    if (null == counter.prev) {
                        counter.prev = current = aIn.readInt(); ++counter.i; ++counter.read;
                        bOut.writeInt(current);
                    } else {
                        if (counter.idle != null) { bOut.writeInt(counter.idle); counter.idle = null; ++counter.read; }
                        if (counter.i < this.size) {
                            current = aIn.readInt(); ++counter.i; ++counter.read;
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
                if (!first) {
                    if (counter.idle != null) { cOut.writeInt(counter.idle); counter.idle = null; }
                    if (counter.i < this.size) {
                        current = aIn.readInt(); ++counter.i; ++counter.read;
                        if (current <= counter.prev == isAscending()) {
                            counter.idle = counter.prev = current;
                            --counter.read;
                            break;
                        } else {
                            counter.prev = current;
                            cOut.writeInt(current);
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

    private void fileMerge(File aFile, File bFile, File cFile, Counter counter) {
        boolean append = counter.append; counter.append = true;
        try(DataOutputStream aOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aFile, append)));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            boolean[] has = {true, true};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            while (i < counter.read) {
                try {
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i;
                } catch (IOException e) {
                    has[0] = false; algoStats.addException();
                }
                if (i < counter.read)
                    try {
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i;
                    } catch (IOException e) {
                        has[1] = false; algoStats.addException();
                    }

                while (!pq.isEmpty()) {
                    HeapNode root = pq.remove();
                    aOut.writeInt(root.val);

                    if (i < counter.read && has[root.id]) {
                        try {
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i;
                        } catch (IOException e) {
                            has[root.id] = false; algoStats.addException();
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

    private void fileMergeOutput(File aFile, File bFile, File cFile, Counter counter) {
        try(BufferedWriter aOut = new BufferedWriter(new FileWriter(aFile));
            DataInputStream bIn = new DataInputStream(new BufferedInputStream(new FileInputStream(bFile)));
            DataInputStream cIn = new DataInputStream(new BufferedInputStream(new FileInputStream(cFile)))) {

            DataInputStream[] in = {bIn, cIn};
            int i = 0;
            boolean[] has = {true, true};
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(2, comparator);

            aOut.write(this.size + "\n");
            while (i < counter.read) {
                try {
                    pq.add(new HeapNode(in[0].readInt(), 0)); ++i;
                } catch (IOException e) {
                    has[0] = false; algoStats.addException();
                }
                if (i < counter.read)
                    try {
                        pq.add(new HeapNode(in[1].readInt(), 1)); ++i;
                    } catch (IOException e) {
                        has[1] = false; algoStats.addException();
                    }

                while (!pq.isEmpty()) {
                    HeapNode root = pq.remove();
                    aOut.write(root.val + " ");

                    if (i < counter.read && has[root.id]) {
                        try {
                            pq.add(new HeapNode(in[root.id].readInt(), root.id)); ++i;
                        } catch (IOException e) {
                            has[root.id] = false; algoStats.addException();
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
