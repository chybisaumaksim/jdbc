package by.chester.dao;
import by.chester.entities.Lesson;
import java.util.List;

public interface LessonDao {

    void create(Lesson lesson) throws PersistException;
    List<Lesson> getAll () throws PersistException;
    void update (Lesson lesson) throws PersistException;
    void delete (Lesson lesson) throws PersistException;
    void close() throws PersistException;
}
