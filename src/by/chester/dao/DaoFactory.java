    package by.chester.dao;
    import by.chester.mySqlDAO.MySqlLessonDao;
    import by.chester.mySqlDAO.MySqlStudentDao;
    import by.chester.mySqlDAO.MySqlMarkDao;

    import java.sql.SQLException;

    public interface DaoFactory {
        MySqlMarkDao getMySqlMarkDao() throws PersistException;

        MySqlStudentDao getMySqlStudentDao() throws PersistException;

        MySqlLessonDao getMySqlLessonDao() throws PersistException, SQLException;
    }


