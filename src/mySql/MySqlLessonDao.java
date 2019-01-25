//package mySql;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.*;
//import java.util.Properties;
//
//public class MySqlLessonDao {
//}
//
//
//package main;
//        import java.io.FileInputStream;
//        import java.io.IOException;
//        import java.sql.*;
//        import java.util.Properties;
//
//public class Main {
//    static final String PATH_TO_PROPERTIES = "src/main/config.properties";
//
//    public static void main(String[] args) {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        FileInputStream fileInputStream= null;
//        try {
//            Properties prop = new Properties();
//            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
//            prop.load(fileInputStream);
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver loaded successful");
//            connection = DriverManager.getConnection(prop.getProperty("url"),
//                    prop.getProperty("login"), prop.getProperty("password"));
//            System.out.println("Connection successful");
//            statement = connection.createStatement();
//            resultSet=statement.executeQuery("SELECT ID, Lesson FROM lessons");
//            while(resultSet.next()) {
//                int ID = resultSet.getInt(1);
//                String lesson = resultSet.getString(2);
//                System.out.println(ID);
//                System.out.println(lesson);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(resultSet!=null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(statement!=null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(connection!=null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(fileInputStream!=null) {
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
