package by.chester.dao;
import java.io.Serializable;
import java.util.List;

public interface GenericDao<Object extends Serializable>{

    void create(Object object) throws PersistException;

    void update(Object object) throws PersistException;

    void delete(Object object) throws PersistException;

    List<Object> getAll() throws PersistException;

}
