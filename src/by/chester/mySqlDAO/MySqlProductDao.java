package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlProductDao {

    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlProductDao(Connection connection) throws PersistException {
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
    public void create(Product product) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, product);
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
                throw new PersistException(" Ошибка закрытия потока", e);
            }
        }
    }
    public List<Product> getAll () throws PersistException {
        ArrayList list= new ArrayList();
        ResultSet rs=null;
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            rs = stm.executeQuery();
            while (rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt(1));
                prod.setCategoryId(rs.getInt(2));
                prod.setPrice(rs.getInt(3));
                prod.setDescription(rs.getString(4));
                list.add(prod);
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
        return  list;
    }
    public void update (Product product) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, product);
            statementUpdate.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete (Product product) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, product);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    private String getSelectAll() {
        return "SELECT ID, Category_Id, Price, Description FROM Product ";
    }
    private String getCreateQuery() {
        return "INSERT INTO Product (Category_Id, Price, Description) VALUES (?, ?, ?); ";
    }
    private String getUpdateQuery(){
        return "UPDATE Product SET  Price = ?, Description = ? WHERE id = ?;";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Product WHERE id= ?;"; }
    private String SelectIdQuery() {
        return "SELECT id, Category_Id, Price, Description WHERE ID = ? ; ";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setInt(1, object.getCategoryId());
            statement.setInt(2, object.getPrice());
            statement.setString(3, object.getDescription());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setInt(1, object.getPrice());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);

        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
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
            throw new PersistException("Ошибка  закрытия statementSelectID ", e);
        }
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException(" Ошибка закрытия Connection ", e);
        }
    }
}
