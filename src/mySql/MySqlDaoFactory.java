package mySql;

import daoFactory.DaoFactory;
import daoFactory.PersistException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDaoFactory implements DaoFactory {

    private static String USER = "root";
    private static String PASSWORD = "root";
    private static String URL = "jdbc:mysql://localhost:3306/students";
    private static String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection() throws PersistException {
        Connection connection=null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }
    public MySqlDaoFactory() {
        try {
            Class.forName(DRIVER);
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public MySqlMarkDao getMySqlMarkDao() throws PersistException {
        return new MySqlMarkDao();
    }
    @Override
    public MySqlStudentDao getMySqlStudentDao() throws PersistException {
        return new MySqlStudentDao();
    }
    @Override
    public MySqlLessonDao getMySqlLessonDao() throws PersistException {
        return new MySqlLessonDao();
    }
}
