package chester.lesson10.mySqlDAO;
import chester.lesson10.dao.DaoFactory;
import chester.lesson10.dao.PersistException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    private static Connection connection;

public MySqlDaoFactory() throws PersistException {
    try {
        MySqlDaoFactory.getConnection();
    } catch (PersistException e) {
        throw new PersistException("Ошибка установки подключения", e);
    }
}

//    static {
//        try {
//            MySqlDaoFactory.getConnection();
//        } catch (PersistException e) {
//            System.err.println("Ошибка установки подключения");
//        }
//    }


    private static Connection getConnection() throws PersistException {
        Properties prop = new Properties();
        InputStream is=null;
        try {
            is = MySqlDaoFactory.class.getClassLoader()
                    .getResourceAsStream("chester/lesson10/resources/config.properties");
            prop.load(is);
            Class.forName(prop.getProperty("driver"));
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
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
                throw new PersistException("Ошибка закрытия потока InputStream", e);
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw new PersistException("Ошибка закрытия потока InputStream", e);
            }
        }
        return connection;
    }


    @Override
    public MySqlMarkDao getMySqlMarkDao() throws PersistException {
        if(connection!=null){
            return new MySqlMarkDao(connection);
        }
        return null;
    }
    @Override
    public MySqlStudentDao getMySqlStudentDao() throws PersistException {
        if(connection!=null){
            return new MySqlStudentDao(connection);
        }
        return null;
    }

    @Override
    public MySqlLessonDao getMySqlLessonDao()throws PersistException {
        if(connection!=null){
            return new MySqlLessonDao(connection);
        }
        return null;
    }
}