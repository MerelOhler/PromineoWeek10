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
    //     String read = "SELECT * FROM book WHERE BookID > ? LIMIT 100";
    //     Scanner kb = new Scanner(System.in);
    //     try {
    //         Connection access = DriverManager.getConnection(DBConn, "root", "qwer1234!");
    //         System.out.println("you are connected");
    //         PreparedStatement statement = access.prepareStatement(read);
    //         String num = kb.nextLine();
    //         statement.setString(1, num);
    //         ResultSet results = statement.executeQuery();
    //         while(results.next()){
    //             System.out.println("OriginalBookName: " + results.getString(2));
    //         }
    //     }
    //     catch(SQLException e){
    //         System.out.println("Access denied");
    //         e.printStackTrace();
    //     }

    //     kb.close();
    
}
