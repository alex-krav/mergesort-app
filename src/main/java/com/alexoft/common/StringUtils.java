package com.alexoft.common;

import java.time.format.DateTimeFormatter;

public abstract class StringUtils {

    public static String generateFilename(String fileName) {
        if (null == fileName)
            fileName = String.format("%s.txt", generateDateTime());
        else
            fileName = String.format("%s_%s.txt", fileName, generateDateTime());
        return fileName;
    }

    /**
     * Generates file name using datetime pattern, like output_2020-05-05_21-13-10.txt
     * @return file name string
     */
    public static String generateDateTime() {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return timeStampPattern.format(java.time.LocalDateTime.now());
    }
}
