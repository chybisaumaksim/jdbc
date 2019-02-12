package by.chester.dao;
import by.chester.entities.Order;
import java.util.List;

public interface OrderDao {

    void create(Order Order) throws PersistException;
    List<Order> getAll () throws PersistException;
    void update (Order Order) throws PersistException;
    void delete (Order Order) throws PersistException;
    void close() throws PersistException;
}
