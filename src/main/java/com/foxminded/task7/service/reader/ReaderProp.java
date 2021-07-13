package com.foxminded.task7.service.reader;

import com.foxminded.task7.service.sql.DataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ReaderProp {

    public static DataSource getProperty(String input) throws IOException {

        Properties properties = new Properties();
        FileInputStream fileStream = new FileInputStream(getFilePath(input));
        properties.load(fileStream);
        String url = (String) properties.get("db.url");
        String username = (String) properties.get("db.username");
        String password = (String) properties.get("db.password");
        String driver = (String) properties.get("db.driver");
        return new DataSource(username, password, url, driver);
    }


    public static String getFilePath(String file) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(file);
        if (url == null) {
            throw new FileNotFoundException("File not found: " + file);
        }
        return url.getPath();
    }
}
