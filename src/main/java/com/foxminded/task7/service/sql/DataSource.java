package com.foxminded.task7.service.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {

    private final String url;
    private final String username;
    private final String password;
    private final String driver;


    public DataSource(String username, String password, String url, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public Connection getConnection()  {

        Connection con;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;

    }
}
