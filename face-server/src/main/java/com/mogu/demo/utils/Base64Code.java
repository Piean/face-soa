package com.mogu.demo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Base64Code {
    public static String encodeImage(Path path) {
        try {
            String fileName = path.getFileName().toString();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            return "data:image/" + suffix + ";base64," + Base64.getEncoder().encodeToString(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
