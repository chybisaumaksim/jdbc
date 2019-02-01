package chester.lesson10.main;
import chester.lesson10.dao.DaoFactory;
import chester.lesson10.dao.PersistException;
import chester.lesson10.mySqlDAO.*;
import chester.lesson10.objectsEntitiesMysql.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PersistException {

        MySqlMarkDao markDao= null;
        MySqlLessonDao lessonDao= null;
        MySqlStudentDao studentDao = null;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            studentDao = factory.getMySqlStudentDao();
            lessonDao = factory.getMySqlLessonDao();
            markDao = factory.getMySqlMarkDao();
//читаем всех студентов
            List<Student> students = studentDao.getAll();
            Iterator iterator = students.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
//получаем все предметы одного студента вместе с их оценками
            List<Mark> marks = markDao.getAll();
            Iterator iterator2 = marks.iterator();
            while (iterator2.hasNext()) {
                System.out.println(iterator2.next());
            }
//получаем все предметы
            List<Lesson> lessons = lessonDao.getAll();
            Iterator iterator3 = lessons.iterator();
            while (iterator3.hasNext()) {
                System.out.println(iterator3.next());
            }
//обновляем студента
            Student st4 = new Student();
            st4.setId(3);
            st4.setName("Virtor");
            st4.setSurname("Pipkin");
            st4.setBirthDate("19890101");
            st4.setEnterYear(2006);
            studentDao.update(st4);
//обновляем предмет
            Lesson ls2 = new Lesson();
            ls2.setId(2);
            ls2.setLesson("Chemistry");
            lessonDao.update(ls2);
//обновляем оценку
            Mark mr2 = new Mark();
            mr2.setId(3);
            mr2.setMark(9);
            markDao.update(mr2);
//добавляем студента
            Student st1 = new Student();
            st1.setName("Dmitrii");
            st1.setSurname("Novoselov");
            st1.setBirthDate("19861009");
            st1.setEnterYear(2008);
            studentDao.create(st1);
//добавляем предмет
            Lesson ls1 = new Lesson();
            ls1.setLesson("Informatics");
            lessonDao.create(ls1);
//добавляем оценку
            Mark mr1 = new Mark();
            mr1.setStudentId(3);
            mr1.setLessonId(2);
            mr1.setMark(10);
            markDao.create(mr1);
//удаляем студента
            Student st5 = new Student();
            st5.setId(13);
            studentDao.delete(st5);
//удаляем оценку
            Mark mr3 = new Mark();
            mr3.setId(6);
            markDao.delete(mr3);
//удаляем предмет
            Lesson ls3 = new Lesson();
            ls3.setId(7);
            lessonDao.delete(ls3);
        }catch (SQLException e) {
            throw new PersistException("Ошибка Sql запроса в классе Main", e);
        }finally {
            try {
                if (studentDao!= null) {
                    studentDao.close();
                }
            }catch (Exception e) {
                throw new PersistException("Ошибка закрытия studentDao", e);
            }
            try {
                if (lessonDao!= null) {
                    lessonDao.close();
                }
            }catch (Exception e) {
                throw new PersistException("Ошибка закрытия lessonDao", e);
            }
            try {
                if (markDao!= null) {
                    markDao.close();
                }
            }catch (Exception e) {
                throw new PersistException("Ошибка закрытия markDao", e);
            }
        }
    }
}
