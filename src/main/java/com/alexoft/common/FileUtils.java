package com.alexoft.common;

import java.io.*;

import static com.alexoft.common.StringUtils.generateFilename;

/**
 * Utility class for work with files.
 */
public abstract class FileUtils {

    /**
     * Creates a copy of file with all contents, substituting
     * "input" in filename with "output" substring
     * @param original original file
     * @return copy of original file
     */
    public static File copy(File original) {
        // update name for copied file
        String name = original.getName();
        if (name.contains("input"))
            name = name.replace("input", "output");
        else
            name = "output_" + name;
        File copied = new File(name);

        // read contents from input file to copy file
        try (InputStream in = new BufferedInputStream(new FileInputStream(original));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return copied;
    }

    /**
     * Saves text to a file.
     * @param fileName name of file
     * @param text string to be written to file
     * @return created file with text
     */
    public static File save(String fileName, String text) {
        File file = new File(generateFilename(fileName));

        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(text);
            out.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
