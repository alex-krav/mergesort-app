package com.alexoft.ui;

import com.alexoft.log.ConsoleLogger;
import com.alexoft.log.FileLogger;
import com.alexoft.random.IntGeneratorImpl;
import com.alexoft.parser.ParserImpl;
import com.alexoft.log.ScreenLogger;
import com.alexoft.sorting.SortingImpl;
import com.alexoft.algo.MultiwayMergeSortImpl;
import com.alexoft.algo.NaturalMergeSortImpl;
import com.alexoft.algo.TopDownImpl;

public class App {
    public static void main(String[] args) {
        Model m = new Model();
        View v = new View();
        Controller c = new Controller(m, v);
        c.initController();

        c.setParser(new ParserImpl());
        c.setIntGenerator(new IntGeneratorImpl());

        SortingImpl sortingService = new SortingImpl();
        sortingService.add(new TopDownImpl());
        sortingService.add(new NaturalMergeSortImpl());
        sortingService.add(new MultiwayMergeSortImpl(2));
        sortingService.add(new MultiwayMergeSortImpl(3));
        sortingService.add(new MultiwayMergeSortImpl(4));

        sortingService.setConsoleLog(new ConsoleLogger());
        sortingService.setFileLog(new FileLogger());
        sortingService.setScreenLog(new ScreenLogger(v.getLogPane()));

        c.setSorting(sortingService);
    }
}
