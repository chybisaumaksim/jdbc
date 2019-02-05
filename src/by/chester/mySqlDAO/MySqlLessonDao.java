package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.objectsEntitiesMysql.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlLessonDao {

    private static Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlLessonDao(Connection connection) throws PersistException {
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
    public void create(Lesson lesson) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, lesson);
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
                throw new PersistException("Ошибка закрытия потока", e);
            }
        }
    }
    public static List<Lesson> getAll() throws PersistException {
        ResultSet rs=null;
        List list = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(getAllQuery())) {
            rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setId(rs.getInt(1));
                l.setLesson(rs.getString(2));
                list.add(l);
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
    public void update (Lesson lesson) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, lesson);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete (Lesson lesson) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, lesson);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    private String getUpdateQuery() {
        return "UPDATE Lesson SET Lesson  = ? WHERE ID = ?";
    }
    private String getCreateQuery() {
        return "INSERT INTO Lesson (ID, Lesson) VALUES (?, ?); ";
    }
    private String getDeleteQuery() {
        return "DELETE FROM Lesson WHERE ID = ?; ";
    }
    private static String getAllQuery() {
        return "SELECT ID, Lesson FROM Lesson ; ";
    }
    private String SelectIdQuery() {
        return "SELECT ID, Lesson FROM Lesson WHERE ID = ? ;";
    }
    private void prepareStatementForInsert(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getLesson());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getLesson());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Lesson object) throws PersistException {
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
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия Connection ", e);
        }
    }

}

