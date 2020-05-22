package com.alexoft.ui;

import com.alexoft.algo.external.Balanced2wayMergeSort;
import com.alexoft.algo.external.DirectMergeSort;
import com.alexoft.algo.external.NaturalMergeSort;
import com.alexoft.log.ScreenLogger;
import com.alexoft.log.TerminalLogger;
import com.alexoft.random.IntGeneratorImpl;
import com.alexoft.sorting.SortingImpl;

/**
 * MVC application to demonstrate merge sort implementations.
 * For View uses Java Swing framework.
 */
public class App {
    /**
     * Initializes and runs application controller.
     * @param args program arguments
     */
    public static void main(String[] args) {
        Model m = new Model();
        View v = new View();
        Controller c = new Controller(m, v);
        c.initController();

        c.setIntGenerator(new IntGeneratorImpl());

        SortingImpl sortingService = new SortingImpl();
        sortingService.add(new DirectMergeSort());
        sortingService.add(new NaturalMergeSort());
        sortingService.add(new Balanced2wayMergeSort());

        sortingService.setTerminalLog(new TerminalLogger());
        sortingService.setScreenLog(new ScreenLogger(v.getLogPane()));

        c.setSorting(sortingService);
    }
}
