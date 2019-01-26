package mySql;

import daoFactory.PersistException;
import domain.Lesson;
import domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MySqlStudentDao{
    private Connection connection;
    private PreparedStatement statementCreate;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementSelectAll;
    private PreparedStatement statementSelectID;
    private PreparedStatement statementDelete;

    protected MySqlStudentDao() throws PersistException {
        try {
            connection=MySqlDaoFactory.getConnection();
            statementCreate = connection.prepareStatement(getCreateQuery());
            statementUpdate = connection.prepareStatement(getUpdateQuery());
            statementSelectAll = connection.prepareStatement(getSelectQuery());
            statementSelectID = connection.prepareStatement(getSelectQuery()+ "WHERE ID = ?;");
            statementDelete = connection.prepareStatement(getDeleteQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update (Student student) throws PersistException {
        try {
            prepareStatementForUpdate(statementUpdate, student);
            statementUpdate.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Student updated успешно");
    }
    private class PersistStudent extends Student {
        public void setId(int id) {
            super.setId(id);
        }
    }
    public Student create(Student student) throws PersistException {
        Student persistInstance;
        ResultSet generatedId;
        ResultSet selectedById;
        try {
            prepareStatementForInsert(statementCreate, student);
            statementCreate.executeUpdate();
            generatedId = statementCreate.getGeneratedKeys();
            if (generatedId.next()) {
                int id = generatedId.getInt(1);
                statementSelectID.setInt(1, id);
            }
            selectedById = statementSelectID.executeQuery();
            List<Student> list = parseResultSet(selectedById);
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("Таблица Student обновлена успешно");
        return persistInstance;
    }
    public void delete(Student student) throws PersistException {
        try {
            prepareStatementForDelete(statementDelete, student);
            statementDelete.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        System.out.println("студент deleted успешно");
    }
    public String getSelectQuery() {
        ArrayList<Student> st=new ArrayList<>();
        return "SELECT ID, First_Name, Second_Name, Birth_Date, Enter_Year FROM student ";
    }
    public  void getAll () {
        System.out.println("Все студенты");
        try (PreparedStatement stm = connection.prepareStatement(getSelectAll())) {
            ResultSet rs = stm.executeQuery();
            List<Student> list = new ArrayList<>();
            Student st = new Student();
            while (rs.next()) {
                st.setId(rs.getInt(1));
                st.setName(rs.getString(2));
                st.setSurname(rs.getString(3));
                st.setBirthDate(rs.getString(4));
                st.setEnterYear(rs.getInt(5));
                list.add(st);
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getCreateQuery() {
        return "INSERT INTO student (First_Name, Second_Name, Birth_Date, Enter_Year) \n  VALUES (?, ?, ?, ?);";
    }
    public String getSelectAll() {
        return "SELECT * FROM STUDENT ";
    }
    public String getUpdateQuery() {
        return "UPDATE Student \n SET First_Name = ?, Second_Name  = ?, Birth_Date = ?, Enter_Year = ? \n WHERE id = ?;";
    }
    public String getDeleteQuery() {
        return "DELETE FROM student WHERE id= ?;";
    }
    protected List<Student> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Student> result = new LinkedList<Student>();
        try {
            while (rs.next()) {
                PersistStudent student = new PersistStudent();
                student.setId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setSurname(rs.getString(3));
                student.setBirthDate(rs.getString(4));
                student.setEnterYear(rs.getInt(5));
                result.add(student);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    protected void prepareStatementForUpdate(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getBirthDate());
            statement.setInt(4, object.getEnterYear());
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForInsert(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getBirthDate());
            statement.setInt(4, object.getEnterYear());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    protected void prepareStatementForDelete(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
