package by.chester.dao;

public interface DaoFactory {

    AddressDao getMySqlAddressDao() throws PersistException;

    CategoryDao getMySqlCategoryDao() throws PersistException;

    CustomerDao getMySqlCustomerDao() throws PersistException;

    OrderDao getMySqlOrderDao() throws PersistException;

    ProductDao getMySqlProductDao() throws PersistException;

    UserDao getMySqlUserDao() throws PersistException;
}


