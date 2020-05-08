package com.alexoft.ui;

public abstract class Validator {

    public static String trim(String name) {
        int extId = name.lastIndexOf('.');
        String ext = (extId > -1) ? name.substring(extId) : "";
        if (name.length() > 30)
            return name.substring(0, 30) + "... " + ext;
        else
            return name;
    }
}
