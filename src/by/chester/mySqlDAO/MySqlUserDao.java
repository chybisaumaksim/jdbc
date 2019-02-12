package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.dao.UserDao;
import by.chester.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {

    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlUserDao (Connection connection) throws PersistException {
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
    public void create(User user) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, user);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
            }
        } catch (Exception e) {
            throw new PersistException("Невозможно записать данные в БД", e);
        }finally {
            try {
                if (generatedId != null) {
                    generatedId.close();
                }
            } catch (SQLException e) {
                throw new PersistException(" Ошибка закрытия потока ", e);
            }
        }
    }
    public List<User> getAll() throws PersistException {
        ArrayList list= new ArrayList();
        ResultSet rs=null;
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())){
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setIsAdmin(rs.getInt(4));
                list.add(user);
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
    public void update(User user) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, user);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete(User user) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, user);
            statementDelete.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия потока ", e);
        }
    }
    private String getCreateQuery() {
        return "INSERT INTO User (Login, Password, Is_admin) VALUES (?, ?, ? ); ";
    }
    private String getSelectAll() {
        return "SELECT ID, Login, Password, Is_admin FROM User ";
    }
    private String getUpdateQuery() {
        return "UPDATE User SET Login = ?, Password = ?, Is_admin = ? WHERE id = ?; ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM User WHERE id= ?;";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Login, Password, Is_admin FROM User WHERE ID = ? ; ";
    }
    private void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getLogin());
            statement.setString(2, object.getPassword());
            statement.setInt(3, object.getIsAdmin());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getLogin());
            statement.setString(2, object.getPassword());
            statement.setInt(3, object.getIsAdmin());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, User object) throws PersistException {
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
