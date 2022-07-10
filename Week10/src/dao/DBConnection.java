package dao;

import java.sql.*;


public class DBConnection {
     private final static String URL = "jdbc:mysql://localhost:3306/mybooks";
     private final static String USERNAME = "root";
     private final static String PASSWORD = "qwer1234!";
     private static Connection connection;
     private static DBConnection instance;

     private DBConnection(Connection connection){
         DBConnection.connection = connection;
     }

     public static Connection getConnection() {
         if (instance == null){
            try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        instance = new DBConnection(connection);
                        System.out.println("connection successful");
                    }
                    catch(SQLException e){
                        System.out.println("Access denied");
                        e.printStackTrace();
                    }
         }
         return DBConnection.connection;
     }    
}
