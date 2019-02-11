package by.chester.dao;
import by.chester.entities.Student;
import java.util.List;

public interface StudentDao{

    void create(Student student) throws PersistException;
    List<Student> getAll () throws PersistException;
    void update (Student student) throws PersistException;
    void delete (Student student) throws PersistException;
    void close() throws PersistException;
}
