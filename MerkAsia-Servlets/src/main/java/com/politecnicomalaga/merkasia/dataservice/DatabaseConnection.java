package com.politecnicomalaga.merkasia.dataservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Provides JDBC connections to the warehouse MySQL database. */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://bbdd:3308/tienda";
    private static final String USER = "tienda_user";
    private static final String PASSWORD = "onlyforyoureyes";
    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";

    /** Utility class — no instances needed. */
    private DatabaseConnection() {}

    /**
     * Opens and returns a new JDBC connection.
     *
     * @return live {@link Connection}
     * @throws SQLException           if the connection cannot be established
     * @throws ClassNotFoundException if the MySQL JDBC driver is not on the classpath
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(CLASSNAME);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
