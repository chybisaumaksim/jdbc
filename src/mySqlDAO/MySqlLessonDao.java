package mySqlDAO;

import dao.PersistException;
import objects.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlLessonDao {

    private Connection connection;
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
        ResultSet generatedId;
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
        }
        System.out.println("Таблица Lesson обновлена успешно");
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
    public String getUpdateQuery() {
        return "UPDATE Lesson SET Lesson  = ?  WHERE ID = ?";
    }
    public String getCreateQuery() {
        return "INSERT INTO Lesson (ID, Lesson) VALUES (?, ?) ; ";
    }
    public String getDeleteQuery() {
        return "DELETE FROM Lesson WHERE id = ?; ";
    }
    public String getAllQuery() {
        return "SELECT ID, Lesson FROM Lesson ; ";
    }
    public String SelectIdQuery() {
        return "SELECT ID, Lesson FROM Lesson WHERE ID = ? ; ";
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
    protected List<Lesson> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Lesson> result = new LinkedList<>();
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

}

