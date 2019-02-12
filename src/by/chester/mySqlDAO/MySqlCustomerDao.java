package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.entities.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCustomerDao {

    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlCustomerDao(Connection connection) throws PersistException {
        try {
            this.connection = connection;
            statementCreate = connection.prepareStatement(getCreateQuery());
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementDelete = connection.prepareStatement(getDeleteQuery());
            statementSelectID = connection.prepareStatement(SelectIdQuery());
        } catch (SQLException e) {
            throw new PersistException("Ошибка при создании prepareStatement в классе " + getClass(), e);
        }
    }
    public void create(Customer customer) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, customer);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
            }
        } catch (Exception e) {
            throw new PersistException(" Невозможно записать данные в БД ", e);
        }finally {
            try {
                if (generatedId != null) {
                    generatedId.close();
                }
            } catch (SQLException e) {
                throw new PersistException("Ошибка закрытия потока ", e);
            }
        }
    }
    public List<Customer> getAll() throws PersistException {
        ArrayList list= new ArrayList();
        ResultSet rs=null;
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())){
            rs = stm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setSurname(rs.getString(3));
                c.setPhoneNumber(rs.getInt(4));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия потока ", e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new PersistException("Ошибка закрытия потока", e);
            }
        }
        return list;
    }
    public void update(Customer customer) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, customer);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete(Customer customer) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, customer);
            statementDelete.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия потока ", e);
        }
    }
    private String getCreateQuery() {
        return "INSERT INTO Customer (Customer_name, Customer_surname, Phone_number) VALUES (?, ?, ?); ";
    }
    private String getSelectAll() {
        return "SELECT ID, Customer_name, Customer_surname, Phone_number FROM Customer ;";
    }
    private String getUpdateQuery() {
        return "UPDATE Student SET Customer_name = ?, Customer_surname  = ?, Phone_number = ? WHERE id = ?; ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Customer WHERE id= ?; ";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Customer_name, Customer_surname, Phone_number FROM Customer WHERE ID = ? ;";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Customer object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setInt(3, object.getPhoneNumber());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Customer object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setInt(3, object.getPhoneNumber());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Customer object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForDelete", e);
        }
    }
    public void close() throws PersistException {
        try {
            if(statementDelete != null)
                statementDelete.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия statementDelete ", e);
        }
        try {
            if(statementCreate != null)
                statementCreate.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия statementCreate", e);
        }
        try {
            if(statementUpdate != null)
                statementUpdate.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия statementUpdate ", e);
        }
        try {
            if(statementSelectID != null)
                statementSelectID.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия statementSelectID ", e);
        }
        try {
            if(connection == null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия Connection", e);
        }
    }
}
