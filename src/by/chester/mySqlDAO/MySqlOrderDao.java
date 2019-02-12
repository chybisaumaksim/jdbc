package by.chester.mySqlDAO;
import by.chester.dao.OrderDao;
import by.chester.dao.PersistException;
import by.chester.entities.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlOrderDao implements OrderDao {

    private static Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlOrderDao (Connection connection) throws PersistException {
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
    public void create(Order order) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, order);
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
                throw new PersistException("Ошибка закрытия потока", e);
            }
        }
    }
    public List<Order> getAll() throws PersistException {
        ResultSet rs=null;
        List list = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            rs = stm.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setProductId(rs.getInt(2));
                order.setAddressId(rs.getInt(3));
                order.setNumber(rs.getInt(4));
                order.setDelivery(rs.getString(5));
                order.setOrderTime(rs.getString(6));
                order.setOrderStatus(rs.getString(7));
                list.add(order);
            }
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
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
    public void update (Order order) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, order);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete (Order order) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, order);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    private String getUpdateQuery() {
        return "UPDATE Orders SET  Number = ?, Delivery = ?, Order_time = ?, Order_status = ? WHERE ID = ? ";
    }
    private String getCreateQuery() {
        return "INSERT INTO Orders (Product_ID, Address_ID, Number, Delivery, Order_time, " +
                " Order_status) VALUES (?, ?, ?, ?, ?, ?); ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Orders WHERE ID = ?; ";
    }
    private String getSelectAll() {
        return "SELECT ID, Product_ID, Address_ID, Number, Delivery, Order_time, Order_status FROM Orders ; ";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Product_ID, Address_ID, Number, Delivery, Order_time, Order_status " +
                "FROM Orders WHERE ID = ? ;";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Order object) throws PersistException {
        try {
            statement.setInt(1, object.getProductId());
            statement.setInt(2, object.getAddressId());
            statement.setInt(3, object.getNumber());
            statement.setString(4, object.getDelivery());
            statement.setString(5, object.getOrderTime());
            statement.setString(6, object.getOrderStatus());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Order object) throws PersistException {
        try {
            statement.setInt(1, object.getNumber());
            statement.setString(2, object.getDelivery());
            statement.setString(3, object.getOrderTime());
            statement.setString(4, object.getOrderStatus());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Order object) throws PersistException {
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
            throw new PersistException(" Ошибка закрытия statementSelectID ", e);
        }
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия Connection ", e);
        }
    }
}
