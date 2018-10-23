package com.mogu.demo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Base64Code {
    public static String encodeImage(Path path) {
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
