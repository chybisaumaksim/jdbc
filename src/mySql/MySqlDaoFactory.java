package mySql;
import daoFactory.DaoFactory;
import daoFactory.PersistException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    static final String PATH_TO_PROPERTIES = "src/domain/config.properties";
    Properties prop = new Properties();
    FileInputStream fileInputStream = null;

        public MySqlDaoFactory() throws PersistException, ClassNotFoundException, IOException {
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            Class.forName(prop.getProperty("driver"));
            System.out.println("Driver loaded successful");
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws PersistException {
        Connection connection;
        Properties prop = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
        } catch (SQLException e) {
            throw new PersistException(e);
        } catch (FileNotFoundException e) {
            throw new PersistException("Файл " + PATH_TO_PROPERTIES
                    + " не найден.");
        } catch (IOException e) {
            throw new PersistException("Ошибка при работе с потоком fileInputStream", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
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
