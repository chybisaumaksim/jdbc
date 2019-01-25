package mySql;

import daoFactory.PersistException;
import main.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlLessonDao  {

    private class PersistLesson extends Lesson {
        public void setId(int id) {
            super.setId(id);
        }
    }
    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementSelectAll;
    private PreparedStatement statementSelectID;
    private PreparedStatement statementDelete;
    protected MySqlLessonDao() throws PersistException {

        try {
            connection=MySqlDaoFactory.getConnection();
            statementCreate = connection.prepareStatement(getCreateQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementSelectAll = connection.prepareStatement(getSelectQuery());
            statementSelectID = connection.prepareStatement(getSelectQuery() + "WHERE ID = ?;");
            statementDelete = connection.prepareStatement(getDeleteQuery());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    public Lesson create(Lesson lesson) throws PersistException {
        Lesson persistInstance;
        ResultSet generatedId=null;
        ResultSet selectedById=null;
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
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //        System.out.println("Все предметы");
//        String sql = "SELECT * FROM lesson;";
//        List<Lesson> list = new ArrayList<Lesson>();
//        try (PreparedStatement stm = connection.prepareStatement(sql)) {
//            ResultSet rs = stm.executeQuery();
//            Lesson l = new Lesson();
//            while (rs.next()) {
//                l.setId(rs.getInt(1));
//                l.setLessonId(rs.getInt(2));
//                l.setLesson(rs.getString(3));
//                list.add(l);
//                System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getString(3));
//            }
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
        return "SELECT id, lesson_Id, lesson FROM Lesson ";
    }
    public String getUpdateQuery() {
        return "UPDATE lesson \n SET Lesson  = ? \n  WHERE lesson_ID = ?";
    }
    public String getCreateQuery() {
        return "INSERT INTO Lesson (lesson_Id, lesson) \n VALUES (?, ?) ;";
    }
    public String getDeleteQuery() {
        return "DELETE FROM lesson WHERE id = ? ;";
    }
    public String getAllQuery() {
        return "SELECT * FROM lesson ; ";
    }
    protected List<Lesson> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Lesson> result = new LinkedList<Lesson>();
        try {
            while (rs.next()) {
                PersistLesson lesson = new PersistLesson();
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
//            statement.setInt(1, object.getId());
            statement.setString(1, object.getLesson());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForUpdate(PreparedStatement statement, Lesson object) throws PersistException {
        try {
            statement.setString(1, object.getLesson());
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
