    package chester.lesson10.dao;
    import chester.lesson10.mySqlDAO.MySqlLessonDao;
    import chester.lesson10.mySqlDAO.MySqlMarkDao;
    import chester.lesson10.mySqlDAO.MySqlStudentDao;

    import java.sql.SQLException;

    public interface DaoFactory {
        MySqlMarkDao getMySqlMarkDao() throws PersistException;

        MySqlStudentDao getMySqlStudentDao() throws PersistException;

        MySqlLessonDao getMySqlLessonDao() throws PersistException, SQLException;
    }


