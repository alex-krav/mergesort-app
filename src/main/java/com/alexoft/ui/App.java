package com.alexoft.ui;

import com.alexoft.log.Logger;
import com.alexoft.log.TerminalLogger;
import com.alexoft.log.FileLogger;
import com.alexoft.random.IntGeneratorImpl;
import com.alexoft.parser.ParserImpl;
import com.alexoft.log.ScreenLogger;
import com.alexoft.sorting.SortingImpl;
import com.alexoft.algo.MultiwayMergeSortImpl;
import com.alexoft.algo.NaturalMergeSortImpl;
import com.alexoft.algo.TopDownImpl;

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
        Logger fileLogger = new FileLogger();
        c.setFileLogger(fileLogger);

        SortingImpl sortingService = new SortingImpl();
        sortingService.add(new TopDownImpl());
        sortingService.add(new NaturalMergeSortImpl());
        sortingService.add(new MultiwayMergeSortImpl(2));
        sortingService.add(new MultiwayMergeSortImpl(3));
        sortingService.add(new MultiwayMergeSortImpl(4));
        sortingService.add(new MultiwayMergeSortImpl(5));
        sortingService.add(new MultiwayMergeSortImpl(6));
        sortingService.add(new MultiwayMergeSortImpl(7));
        sortingService.add(new MultiwayMergeSortImpl(8));

        sortingService.setConsoleLog(new TerminalLogger());
        sortingService.setFileLog(fileLogger);
        sortingService.setScreenLog(new ScreenLogger(v.getLogPane()));

        c.setSorting(sortingService);
    }
}
