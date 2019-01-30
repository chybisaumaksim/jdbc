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
    private static Connection connection;

    static {
        try {
        MySqlDaoFactory.getConnection();
    } catch (PersistException e) {
        e.printStackTrace();
    }
}

    private static Connection getConnection() throws PersistException {
        Properties prop = new Properties();
        InputStream is=null;
        try {
            is = MySqlDaoFactory.class.getClassLoader()
                    .getResourceAsStream("resources/config.properties");
            prop.load(is);
            Class.forName(prop.getProperty("driver"));
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
            System.out.println("dtutrewertyuytr");
        } catch (SQLException e) {
            throw new PersistException("Ошибка подключения к БД", e);
        } catch (FileNotFoundException e) {
            throw new PersistException("Файл config.properties не найден.", e);
        } catch (IOException e) {
            throw new PersistException("Ошибка при работе с потоком fileInputStream", e);
        } catch (ClassNotFoundException e) {
            throw new PersistException("Класс "+prop.getProperty("driver")+" не найден", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw new PersistException("Ошибка закрытия потока", e);
            }
        }
        return connection;
    }

    @Override
    public MySqlMarkDao getMySqlMarkDao() throws PersistException {
        return new MySqlMarkDao(connection);
    }
    @Override
    public MySqlStudentDao getMySqlStudentDao() throws PersistException {
        return new MySqlStudentDao(connection);
    }
    @Override
    public MySqlLessonDao getMySqlLessonDao()throws PersistException {
        return new MySqlLessonDao(connection);
    }
}
