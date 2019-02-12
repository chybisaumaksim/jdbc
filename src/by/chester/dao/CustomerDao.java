package by.chester.dao;
import by.chester.entities.Customer;
import java.util.List;

public interface CustomerDao {

    void create(Customer customer) throws PersistException;
    List<Customer> getAll () throws PersistException;
    void update (Customer customer) throws PersistException;
    void delete (Customer customer) throws PersistException;
    void close() throws PersistException;
}
