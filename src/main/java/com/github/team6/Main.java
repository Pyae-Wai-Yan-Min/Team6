package com.github.team6;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args){
        java.sql.Connection con = Connection.connect();

        if (con == null){
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }

        try{
            System.out.println("Running application");
        }
        finally
        {
            Connection.disconnect(con);
        }
    }

}
