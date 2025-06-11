package com.acledabank.student_management_api.util;

public class StringClassUtil {
    private StringClassUtil() {
    }

    public static String getFileExtension(String file) {
        int index = file.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        return file.substring(index + 1);
    }
}
