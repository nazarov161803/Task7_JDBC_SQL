package com.foxminded.task7;

import com.foxminded.task7.controller.UserInterface;
import com.foxminded.task7.service.reader.ReaderProp;
import com.foxminded.task7.service.sql.DataSource;

import java.io.IOException;

public class SchoolApplication {

    private static final String DATA_BASE_PROP = "application.properties";


    public static void main(String[] args) throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);

        UserInterface userInterface = new UserInterface(dataSource);
        userInterface.runConsole();
    }
}
