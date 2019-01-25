package mySql;

import daoFactory.DaoFactory;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {

    static Connection connection = null;
    static FileInputStream fileInputStream= null;
    static Properties prop = new Properties();

    public MySqlDaoFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        final String PATH_TO_PROPERTIES = "src/main/config.properties";
        try {

            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
            System.out.println("Connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }




//    @Override
//    public MySqlMarkDao getMySqlMarkDao() throws PersistException {
//        return new MySqlMarkDao();
//    }
//    @Override
//    public MySqlStudentDao getMySqlStudentDao() throws PersistException {
//        return new MySqlStudentDao();
//    }
//    @Override
//    public MySqlLessonDao getMySqlLessonDao() throws PersistException, SQLException {
//        return new MySqlLessonDao();
//    }
}
