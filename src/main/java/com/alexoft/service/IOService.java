package com.alexoft.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IOService {

    int[] readFile(File file) throws FileNotFoundException;
    int[] readString(String str);
    void writeFile(String fileName, int[] numbers) throws IOException;
    void writeFile(int[] numbers) throws IOException;
}
