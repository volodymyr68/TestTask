package com.senchenko.person;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtil {

    public static String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return getResourceFileAsString(classLoader, fileName);
    }

    @SneakyThrows
    public static String getResourceFileAsString(ClassLoader classLoader, String fileName) {
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException(fileName);
            }

            try (InputStreamReader isr = new InputStreamReader(inputStream); BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}