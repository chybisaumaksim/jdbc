package by.chester.dao;
import by.chester.entities.Category;
import java.util.List;

public interface CategoryDao {

    void create(Category category) throws PersistException;
    List<Category> getAll () throws PersistException;
    void update (Category category) throws PersistException;
    void delete (Category category) throws PersistException;
    void close() throws PersistException;
}
