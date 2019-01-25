package mySql;
import daoFactory.PersistException;
import domain.Mark;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

    public class MySqlMarkDao {
    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementSelectID;
    private PreparedStatement statementDelete;

    protected MySqlMarkDao() throws PersistException {

        try {
            connection=MySqlDaoFactory.getConnection();
            statementCreate = connection.prepareStatement(getCreateQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementSelectID = connection.prepareStatement(getSelectQuery() + "WHERE ID = ?;");
            statementDelete = connection.prepareStatement(getDeleteQuery());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    private class PersistMark extends Mark {
        public void setId(int id) {
            super.setId(id);
        }
    }
    public Mark create(Mark mark) throws PersistException {
        Mark persistInstance;
        ResultSet generatedId=null;
        ResultSet selectedById=null;
        try {
            prepareStatementForInsert(statementCreate, mark);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
            }
            selectedById = statementSelectID.executeQuery();
            List<Mark> list = parseResultSet(selectedById);
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Mark обновлена успешно");
        return persistInstance;
    }
    public void getAll () {
        System.out.println("Все оценки");
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update (Mark mark) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, mark);
            statementUpdate.executeUpdate();

        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Mark updated успешно");
    }
    public void delete (Mark mark) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, mark);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Оценка deleted успешно");
    }
    public String getSelectAll() {
        return "SELECT * FROM mark ";
    }
    public String getSelectQuery() {
        return "SELECT id, student_Id, lesson_Id, mark FROM Mark ";
    }
    public String getCreateQuery() {
        return "INSERT INTO Mark (student_Id, lesson_Id, mark) \n VALUES (?, ?, ?);";
    }
    public String getUpdateQuery() throws PersistException {
        return "UPDATE STUDENTS.mark \n SET MARK = ? WHERE id = ?;";
    }
    public String getDeleteQuery() {return "DELETE FROM Mark WHERE id= ?;";
    }
    protected List<Mark> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Mark> result = new LinkedList<Mark>();
        try {
            while (rs.next()) {
                PersistMark mark = new PersistMark();
                mark.setId(rs.getInt("Id"));
                mark.setStudentId(rs.getInt(2));
                mark.setLessonId(rs.getInt(3));
                mark.setMark(rs.getInt(4));
                result.add(mark);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    protected void prepareStatementForInsert(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(1, object.getStudentId());
            statement.setInt(2, object.getLessonId());
            statement.setInt(3, object.getMark());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForUpdate(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(2, object.getId());
            statement.setInt(1, object.getMark());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForDelete(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}

