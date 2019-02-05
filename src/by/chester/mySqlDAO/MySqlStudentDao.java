package by.chester.mySqlDAO;
import by.chester.dao.PersistException;
import by.chester.objectsEntitiesMysql.Mark;
import by.chester.objectsEntitiesMysql.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentDao {
    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementDelete;
    private PreparedStatement statementSelectID;

    protected MySqlStudentDao(Connection connection) throws PersistException {
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
    public void update(Student student) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, student);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса", e);
        }
    }
    public void create(Student student) throws PersistException {
        ResultSet generatedId = null;
        try {
            prepareStatementForInsert(statementCreate, student);
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
                throw new PersistException("Ошибка закрытия потока ", e);
            }
        }
    }
    public void delete(Student student) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, student);
            statementDelete.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException("Ошибка закрытия потока ", e);
        }
    }
    public List<Student> getAll() throws PersistException {
        ArrayList list= new ArrayList();
        ResultSet rs=null;
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())){
            rs = stm.executeQuery();
            while (rs.next()) {
//                list.add(rs.getInt(1)+" "
//                        +rs.getString(2)+" "+rs.getString(3)
//                        +" "+rs.getString(4)+" "+rs.getInt(5));
                Student st = new Student();
                st.setId(rs.getInt(1));
                st.setName(rs.getString(2));
                st.setSurname(rs.getString(3));
                st.setBirthDate(rs.getString(4));
                st.setEnterYear(rs.getInt(5));
                list.add(st);
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
        return "INSERT INTO student (First_Name, Second_Name, Birth_Date, Enter_Year) \n  VALUES (?, ?, ?, ?);";
    }
    private String getSelectAll() {
        return "SELECT ID, First_Name, Second_Name, Birth_Date, Enter_Year FROM STUDENT ";
    }
    private String getUpdateQuery() {
        return "UPDATE Student SET First_Name = ?, Second_Name  = ?, Birth_Date = ?, Enter_Year = ? WHERE id = ?;";
    }
    private String getDeleteQuery() {
        return "DELETE FROM STUDENT WHERE id= ?;";
    }
    private String SelectIdQuery() {
        return "SELECT ID, First_Name, Second_Name, Birth_Date, Enter_Year FROM STUDENT WHERE ID = ? ; ";
    }
    private void prepareStatementForUpdate(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getBirthDate());
            statement.setInt(4, object.getEnterYear());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForUpdate", e);
        }
    }
    private void prepareStatementForInsert(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getBirthDate());
            statement.setInt(4, object.getEnterYear());
        } catch (SQLException e) {
            throw new PersistException("Ошибка получения prepareStatementForInsert", e);
        }
    }
    private void prepareStatementForDelete(PreparedStatement statement, Student object) throws PersistException {
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

