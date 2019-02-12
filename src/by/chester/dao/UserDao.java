package by.chester.dao;
import by.chester.entities.User;
import java.util.List;

public interface UserDao {

    void create(User user) throws PersistException;
    List<User> getAll () throws PersistException;
    void update (User user) throws PersistException;
    void delete (User user) throws PersistException;
    void close() throws PersistException;
}
