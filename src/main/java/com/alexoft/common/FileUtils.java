package com.alexoft.common;

import java.io.*;

import static com.alexoft.common.StringUtils.generateFilename;

public abstract class FileUtils {

    public static File copy(File original) {
        String name = original.getName();
        if (name.contains("input"))
            name = name.replace("input", "output");
        else
            name = "output_" + name;
        File copied = new File(name);

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
