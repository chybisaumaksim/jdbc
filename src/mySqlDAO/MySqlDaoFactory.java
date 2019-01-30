package mySqlDAO;
import dao.DaoFactory;
import dao.PersistException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    Properties prop = new Properties();
    InputStream is =null;
        public MySqlDaoFactory() throws PersistException{
        try {
            is = this.getClass().getClassLoader()
                    .getResourceAsStream("resources/config.properties");
            prop.load(is);
            Class.forName(prop.getProperty("driver"));
            System.out.println("Driver loaded successful");
        } catch (FileNotFoundException e) {
            throw new PersistException("Файл config.properties не найден.", e);
        } catch (IOException e) {
            throw new PersistException("Ошибка при работе с потоком InputStream", e);
        } catch (ClassNotFoundException e) {
            throw new PersistException("Класс "+prop.getProperty("driver")+" не найден", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws PersistException {
        Connection connection;
        Properties prop = new Properties();
        InputStream is=null;
        try {
            is = MySqlDaoFactory.class.getClassLoader()
                    .getResourceAsStream("resources/config.properties");
            prop.load(is);
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
        } catch (SQLException e) {
            throw new PersistException("Ошибка подключения к БД", e);
        } catch (FileNotFoundException e) {
            throw new PersistException("Файл config.properties не найден.", e);
        } catch (IOException e) {
            throw new PersistException("Ошибка при работе с потоком fileInputStream", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  connection;
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
    public MySqlLessonDao getMySqlLessonDao()throws PersistException {
        return new MySqlLessonDao();
    }
}
