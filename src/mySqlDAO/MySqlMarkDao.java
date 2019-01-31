package mySqlDAO;
import dao.PersistException;
import objects.Mark;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class MySqlMarkDao {
    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlMarkDao(Connection connection) throws PersistException {
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
    public void create(Mark mark) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, mark);
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
    public void getAll () throws PersistException {
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void update (Mark mark) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, mark);
            statementUpdate.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void delete (Mark mark) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, mark);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    private String getSelectAll() {
        return "SELECT id, student_Id, lesson_Id, mark FROM mark ";
    }
    private String getCreateQuery() {
        return "INSERT INTO Mark (student_Id, lesson_Id, mark) VALUES (?, ?, ?);";
    }
    private String getUpdateQuery(){
        return "UPDATE STUDENTS.mark  SET MARK = ? WHERE id = ?;";
    }
    private String getDeleteQuery() {return "DELETE FROM Mark WHERE id= ?;"; }
    private String SelectIdQuery() {
            return "SELECT id, student_Id, lesson_Id, mark FROM mark WHERE ID = ? ; ";
        }
    private void prepareStatementForInsert(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(1, object.getStudentId());
            statement.setInt(2, object.getLessonId());
            statement.setInt(3, object.getMark());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(2, object.getId());
            statement.setInt(1, object.getMark());
        } catch (Exception e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);

        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Mark object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new PersistException("Ошибка получения prepareStatementForDelete", e);

        }
    }
}

