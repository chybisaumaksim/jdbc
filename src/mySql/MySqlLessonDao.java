package mySql;

import daoFactory.PersistException;
import domain.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlLessonDao  {

    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementSelectAll;
    private PreparedStatement statementSelectID;
    private PreparedStatement statementDelete;
    protected MySqlLessonDao() {
        try {
            connection=MySqlDaoFactory.getConnection();
            statementCreate = connection.prepareStatement(getCreateQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementSelectAll = connection.prepareStatement(getSelectQuery());
            statementSelectID = connection.prepareStatement(getSelectQuery() + "WHERE ID = ?;");
            statementDelete = connection.prepareStatement(getDeleteQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Lesson create(Lesson lesson) throws PersistException {
        Lesson persistInstance;
        ResultSet generatedId;
        ResultSet selectedById;
        try {
            prepareStatementForInsert(statementCreate, lesson);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
            }
            selectedById = statementSelectID.executeQuery();
            List<Lesson> list = parseResultSet(selectedById);
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Lesson обновлена успешно");
        return persistInstance;
    }
    public void getAll () {
        System.out.println("Все предметы");
        try (PreparedStatement stm = connection.prepareStatement(getAllQuery())) {
            ResultSet rs = stm.executeQuery();
            List<Lesson> list = new ArrayList<>();
            Lesson l = new Lesson();
            while (rs.next()) {
                l.setId(rs.getInt(1));
                l.setLesson(rs.getString(2));
                list.add(l);
                System.out.println(rs.getInt(1)+" "+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update (Lesson lesson) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, lesson);
            statementUpdate.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Lesson updated успешно");
    }
    public void delete (Lesson lesson) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, lesson);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Lesson deleted успешно");
    }
    public String getSelectQuery() {
        return "SELECT ID, Lesson FROM Lesson ";
    }
    public String getUpdateQuery() {
        return "UPDATE Lesson \n SET Lesson  = ? \n  WHERE ID = ?";
    }
    public String getCreateQuery() {
        return "INSERT INTO Lesson (ID, Lesson) \n VALUES (?, ?) ;";
    }
    public String getDeleteQuery() {
        return "DELETE FROM Lesson WHERE id = ? ;";
    }
    public String getAllQuery() {
        return "SELECT * FROM lesson ; ";
    }
    protected List<Lesson> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Lesson> result = new LinkedList<Lesson>();
        try {
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt(1));
                lesson.setLesson(rs.getString(2));
                result.add(lesson);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    protected void prepareStatementForInsert(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getLesson());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForUpdate(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getLesson());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForDelete(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
