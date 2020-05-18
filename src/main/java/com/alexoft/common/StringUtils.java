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

    /**
     * Trims input file name to 40 characters before displaying it on view,
     * so that it fits on panel
     * @param name file name
     * @return trimmed version of file name
     */
    public static String trim(String name) {
        int extId = name.lastIndexOf('.');
        String ext = (extId > -1) ? name.substring(extId) : "";
        if (name.length() > 40)
            return name.substring(0, 40) + "... " + ext;
        else
            return name;
    }
}
