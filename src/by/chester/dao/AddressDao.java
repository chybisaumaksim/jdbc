package by.chester.dao;
import by.chester.entities.Address;
import java.util.List;

public interface AddressDao {

    void create(Address address) throws PersistException;
    List<Address> getAll () throws PersistException;
    void update (Address address) throws PersistException;
    void delete (Address address) throws PersistException;
    void close() throws PersistException;
}
