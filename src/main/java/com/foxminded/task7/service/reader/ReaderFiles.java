package com.foxminded.task7.service.reader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderFiles {

    public List <String> readFile(String fileName) {

        checkString(fileName);
        ClassLoader classLoader = ReaderFiles.class.getClassLoader();

        URL url = classLoader.getResource(fileName);
        checkUrl(url);

        File file = new File(url.getFile());
        checkFile(file);


        List<String> listLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            listLines = stream.collect(Collectors.toList());
            checkList(listLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLines;
    }

    private void checkUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("URL must not be null");
        }
    }

    private void checkFile(File file) {
        System.out.println(file);
        if (!file.exists() || !file.canRead() || file.getAbsolutePath().isEmpty()) {
            throw new IllegalArgumentException("File must be exist and readable");
        }
    }

    private void checkList(List<String> listText) {
        if (listText.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
    }

    private void checkString(String log) {
        if (log == null) {
            throw new IllegalArgumentException("File must not be null");
        }
    }
}
