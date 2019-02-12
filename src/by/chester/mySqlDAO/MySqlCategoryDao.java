package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.entities.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryDao {

    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlCategoryDao(Connection connection) throws PersistException {
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
    public void update(Category category) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, category);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void create(Category category) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, category);
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
                throw new PersistException("Ошибка закрытия потока ", e);
            }
        }
    }
    public void delete(Category category) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, category);
            statementDelete.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия потока ", e);
        }
    }
    public List<Category> getAll() throws PersistException {
        ArrayList list= new ArrayList();
        ResultSet rs=null;
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())){
            rs = stm.executeQuery();
            while (rs.next()) {
                Category ct = new Category();
                ct.setId(rs.getInt(1));
                ct.setCategoryName(rs.getString(2));
                list.add(ct);
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
    private String getCreateQuery() {
        return "INSERT INTO Category Category_name VALUES ?; ";
    }
    private String getSelectAll() {
        return "SELECT ID, Category_name FROM Category ;";
    }
    private String getUpdateQuery() {
        return "UPDATE Category SET Category_name = ? WHERE id = ?; ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Category WHERE id= ?; ";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Category_Name FROM Category WHERE ID = ?; ";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Category object) throws PersistException {
        try {
            statement.setString(1, object.getCategoryName());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Category object) throws PersistException {
        try {
            statement.setString(1, object.getCategoryName());
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Category object) throws PersistException {
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
            if(connection == null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия Connection", e);
        }
    }
}
