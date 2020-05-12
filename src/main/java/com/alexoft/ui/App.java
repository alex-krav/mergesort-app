package com.alexoft.ui;

import com.alexoft.algo.FourWayMergeSort;
import com.alexoft.algo.NaturalMergeSort;
import com.alexoft.algo.ThreeWayMergeSort;
import com.alexoft.algo.BinaryMergeSort;
import com.alexoft.log.FileLogger;
import com.alexoft.log.ScreenLogger;
import com.alexoft.log.TerminalLogger;
import com.alexoft.parser.ParserImpl;
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

        c.setParser(new ParserImpl());
        c.setIntGenerator(new IntGeneratorImpl());

        SortingImpl sortingService = new SortingImpl();
        sortingService.add(new BinaryMergeSort());
        sortingService.add(new NaturalMergeSort());
        sortingService.add(new ThreeWayMergeSort());
        sortingService.add(new FourWayMergeSort());

        sortingService.setConsoleLog(new TerminalLogger());
        sortingService.setFileLog(new FileLogger());
        sortingService.setScreenLog(new ScreenLogger(v.getLogPane()));

        c.setSorting(sortingService);
    }
}
