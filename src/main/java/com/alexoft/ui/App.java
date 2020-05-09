package com.alexoft.ui;

import com.alexoft.service.GeneratorServiceImpl;
import com.alexoft.service.IOServiceImpl;
import com.alexoft.service.LoggingServiceImpl;
import com.alexoft.service.SortingServiceImpl;
import com.alexoft.sorting.MultiwayMergeSortImpl;
import com.alexoft.sorting.NaturalMergeSortImpl;
import com.alexoft.sorting.TopDownImpl;

public class App {
    public static void main(String[] args) {
        Model m = new Model();
        View v = new View();
        Controller c = new Controller(m, v);
        c.initController();

        c.setIoService(new IOServiceImpl());
        c.setGeneratorService(new GeneratorServiceImpl());
        c.setLoggingService(new LoggingServiceImpl(v.getLogPane()));

        SortingServiceImpl sortingService = new SortingServiceImpl();
        sortingService.add(new TopDownImpl());
        sortingService.add(new NaturalMergeSortImpl());
        sortingService.add(new MultiwayMergeSortImpl(2));
        sortingService.add(new MultiwayMergeSortImpl(3));
        sortingService.add(new MultiwayMergeSortImpl(4));
        c.setSortingService(sortingService);
    }
}
