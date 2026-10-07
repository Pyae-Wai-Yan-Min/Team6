package com.github.team6;

import java.sql.*;

public class Connection {

    /**
     * Establishes a connection to the MySQL database.
     * Retries up to 10 times with a delay to allow the database container time to spin up.
     */
    public static java.sql.Connection connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        java.sql.Connection con = null;
        int retries = 10;

        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait for DB container initialization
                Thread.sleep(30000);

                // Connect to the 'world' database using user 'root' and password '12345'
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "rootpsw"
                );

                System.out.println("Successfully connected");
                break; // Exit retry loop on successful connection
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        return con;
    }

    public static void disconnect(java.sql.Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
}