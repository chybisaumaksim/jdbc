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
        System.out.println("Таблица Mark обновлена успешно");
    }
    public void getAll () {
        System.out.println("все предметы одного студента вместе с их оценками");
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
        return "SELECT id, student_Id, lesson_Id, mark FROM mark ";
    }
    public String getCreateQuery() {
        return "INSERT INTO Mark (student_Id, lesson_Id, mark) VALUES (?, ?, ?);";
    }
    public String getUpdateQuery(){
        return "UPDATE STUDENTS.mark  SET MARK = ? WHERE id = ?;";
    }
    public String getDeleteQuery() {return "DELETE FROM Mark WHERE id= ?;";
    }
        public String SelectIdQuery() {
            return "SELECT id, student_Id, lesson_Id, mark FROM mark WHERE ID = ? ; ";
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

