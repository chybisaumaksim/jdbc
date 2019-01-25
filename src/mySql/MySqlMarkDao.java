package mySql;
import main.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMarkDao {
        private Connection connection;
        public Mark create() {
            return null;
        }
        public Mark read(int key) throws SQLException {
            String sql = "SELECT ID, Student_ID, Lesson_ID, Mark FROM marks WHERE id = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, key);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Mark m = new Mark();
            m.setId(rs.getInt(1));
            m.setStudentId(rs.getInt(2));
            m.setLessonId(rs.getInt(3));
            m.setMark(rs.getInt(4));
            return m;
        }
        public void update(Mark mark) {

        }
        public void delete(Mark mark) {

        }
        public List<Mark> getAll() throws SQLException {
            String sql = "SELECT * FROM daotalk.Group;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            List<Mark> list = new ArrayList<Mark>();
            while (rs.next()) {
                Mark m = new Mark();
                m.setId(rs.getInt(1));
                m.setStudentId(rs.getInt(2));
                m.setLessonId(rs.getInt(3));
                m.setMark(rs.getInt(4));
                list.add(m);
            }
            return list;
        }
        public MySqlMarkDao(Connection connection) {
            this.connection = connection;
        }
    }




