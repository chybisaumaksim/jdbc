package by.chester.dao;
import by.chester.entities.Product;
import java.util.List;

public interface ProductDao {

    void create(Product product) throws PersistException;
    List<Product> getAll () throws PersistException;
    void update (Product product) throws PersistException;
    void delete (Product product) throws PersistException;
    void close() throws PersistException;
}
