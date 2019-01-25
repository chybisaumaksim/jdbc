package daoFactory;

import mySql.MySqlLessonDao;
import mySql.MySqlMarkDao;
import mySql.MySqlStudentDao;
import java.sql.SQLException;

public interface DaoFactory {
    MySqlMarkDao getMySqlMarkDao() throws PersistException;

    MySqlStudentDao getMySqlStudentDao() throws PersistException;

    MySqlLessonDao getMySqlLessonDao() throws PersistException, SQLException;
}


