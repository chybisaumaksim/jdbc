package by.chester.mySqlDAO;
import by.chester.dao.AddressDao;
import by.chester.dao.PersistException;
import by.chester.entities.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlAddressDao implements AddressDao {

    private static Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlAddressDao(Connection connection) throws PersistException {
        try {
            this.connection = connection;
            statementCreate = connection.prepareStatement(getCreateQuery());
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementDelete = connection.prepareStatement(getDeleteQuery());
            statementSelectID = connection.prepareStatement(SelectIdQuery());
        } catch (SQLException e) {
            throw new PersistException("Ошибка при создании prepareStatement в классе "+getClass(), e);
        }
    }
    public void create(Address address) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, address);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
             }
        } catch (Exception e) {
            throw new PersistException("Невозможно записать данные в БД ", e);
        }finally {
            try {
                if (generatedId != null) {
                    generatedId.close();
                }
            } catch (SQLException e) {
                throw new PersistException("Ошибка закрытия потока", e);
            }
        }
    }
    public List<Address> getAll() throws PersistException {
        ResultSet rs=null;
        List list = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            rs = stm.executeQuery();
            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt(1));
                address.setCustomerId(rs.getInt(2));
                address.setStreet(rs.getString(3));
                address.setHouseNumber(rs.getInt(4));
                address.setPorchNumber(rs.getInt(5));
                address.setFloorNumber(rs.getInt(6));
                address.setApartmentNumber(rs.getInt(7));
                list.add(address);
            }
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса ", e);
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
    public void update (Address address) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, address);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete (Address address) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, address);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    private String getUpdateQuery() {
        return "UPDATE Address SET Street = ?, House_number = ?, " +
                "Porch_number = ?, Floor_number = ?, Apartment_number = ? WHERE ID = ? ";
    }
    private String getCreateQuery() {
        return "INSERT INTO Address (Customer_Id, Street, House_number, Porch_number, " +
                "Floor_number, Apartment_number) VALUES (?, ?, ?, ?, ?, ? ); ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Address WHERE ID = ?; ";
    }
    private String getSelectAll() {
        return "SELECT ID, Customer_Id, Street, House_number, Porch_number, " +
                "Floor_number, Apartment_number FROM Address ; ";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Customer_Id, Street, House_number, Porch_number, Floor_number, " +
                "Apartment_number FROM Address WHERE ID = ?; ";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Address object) throws PersistException {
        try {
            statement.setInt(1, object.getCustomerId());
            statement.setString(2, object.getStreet());
            statement.setInt(3, object.getHouseNumber());
            statement.setInt(4, object.getPorchNumber());
            statement.setInt(5, object.getFloorNumber());
            statement.setInt(6, object.getApartmentNumber());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Address object) throws PersistException {
        try {
            statement.setString(1, object.getStreet());
            statement.setInt(2, object.getHouseNumber());
            statement.setInt(3, object.getPorchNumber());
            statement.setInt(4, object.getFloorNumber());
            statement.setInt(5, object.getApartmentNumber());
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Address object) throws PersistException {
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
            throw new PersistException("Ошибка закрытия statementDelete", e);
        }
        try {
            if(statementCreate != null)
                statementCreate.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия statementCreate ", e);
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
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия Connection ", e);
        }
    }
}

