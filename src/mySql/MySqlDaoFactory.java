package mySql;
import daoFactory.DaoFactory;
import daoFactory.PersistException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    static final String PATH_TO_PROPERTIES = "src/domain/config.properties";



    public static Connection getConnection() {
            Connection connection=null;
        try {

            connection = DriverManager.getConnection("Url");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  connection;
    }
        public MySqlDaoFactory() {
        try {
            Properties prop = new Properties();
            new FileInputStream(PATH_TO_PROPERTIES);
            Class.forName(prop.getProperty("driver"));
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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
