package dao;

import mySqlDAO.MySqlLessonDao;
import mySqlDAO.MySqlMarkDao;
import mySqlDAO.MySqlStudentDao;
import java.sql.SQLException;

public interface DaoFactory {
    MySqlMarkDao getMySqlMarkDao() throws PersistException;

    MySqlStudentDao getMySqlStudentDao() throws PersistException;

    MySqlLessonDao getMySqlLessonDao() throws PersistException, SQLException;
}


