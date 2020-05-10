package com.alexoft.parser;

import java.io.File;
import java.io.FileNotFoundException;

public interface Parser {

    int[] readFile(File file) throws FileNotFoundException;
    int[] readString(String str);
}
