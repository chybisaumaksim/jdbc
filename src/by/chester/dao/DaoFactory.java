package by.chester.dao;

public interface DaoFactory {

    MarkDao getMySqlMarkDao() throws PersistException;

    StudentDao getMySqlStudentDao() throws PersistException;

    LessonDao getMySqlLessonDao() throws PersistException;
}


