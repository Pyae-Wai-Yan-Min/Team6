package com.github.team6;

import java.sql.*;

public class Connection {

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

        // Note: Your Dockerfile correctly sets the root password to 'rootpsw' which matches below
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database... (Attempt " + i + ")");
            try {
                // TRY 1: Local testing (IntelliJ)
                // This connects via the exposed port 33060 defined in docker-compose.yaml
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "rootpsw"
                );
                System.out.println("Successfully connected to Local Database!");
                break; // Exit retry loop on successful connection

            } catch (SQLException sqle) {
                try {
                    // TRY 2: Docker deployment
                    // If localhost fails, it means the app is running inside Docker, so try 'db'
                    con = DriverManager.getConnection(
                            "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                            "root",
                            "rootpsw"
                    );
                    System.out.println("Successfully connected to Docker Database!");
                    break; // Exit retry loop on successful connection

                } catch (SQLException sqle2) {
                    System.out.println("Failed to connect. Retrying...");
                }
            }

            // Wait 3 seconds before retrying (Moved here so it doesn't delay the very first attempt)
            try {
                Thread.sleep(3000);
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