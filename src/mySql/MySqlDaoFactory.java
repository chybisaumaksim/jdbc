package mySql;
import daoFactory.DaoFactory;
import daoFactory.PersistException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    static final String PATH_TO_PROPERTIES = "src/domain/config.properties";
    Properties prop = new Properties();
    FileInputStream fileInputStream = null;

    public MySqlDaoFactory() {
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            Class.forName(prop.getProperty("driver"));
            System.out.println("Driver loaded successful");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        Connection connection=null;
        Properties prop = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            connection = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"), prop.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
    public MySqlLessonDao getMySqlLessonDao() throws PersistException {
        return new MySqlLessonDao();
    }
}
