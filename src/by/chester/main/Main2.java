package by.chester.main;
import by.chester.dao.*;
import by.chester.mySqlDAO.MySqlDaoFactory;
import by.chester.entities.Lesson;
import by.chester.entities.Mark;
import by.chester.entities.Student;

import java.util.List;

public class Main2 {
    public static void main(String[] args) throws PersistException {

        MarkDao markDao= null;
        LessonDao lessonDao= null;
        StudentDao studentDao = null;




        try {
            DaoFactory factory = new MySqlDaoFactory();
            studentDao = factory.getMySqlStudentDao();
            lessonDao = factory.getMySqlLessonDao();
            markDao = factory.getMySqlMarkDao();
//читаем всех студентов
            List<Student> students = studentDao.getAll();
            for (Student st: students) {
                System.out.println(st.getId()+" "+st.getName()+" "+st.getSurname()
                        +" "+st.getBirthDate()+" "+st.getEnterYear());
            }
//получаем все предметы одного студента вместе с их оценками
            List<Mark> marks = markDao.getAll();
            for (Mark mk: marks) {
                System.out.println(mk.getId()+" "+mk.getStudentId()+" "
                        +mk.getLessonId()+" "+mk.getMark());
            }
//получаем все предметы
            List<Lesson> lessons = lessonDao.getAll();
            for (Lesson ls: lessons) {
                System.out.println(ls.getId()+" "+ls.getLesson());
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
        }catch (PersistException e) {
            throw new PersistException("Ошибка Sql запроса в классе Main", e);
        }finally {
            try {
                if (studentDao != null) {
                    studentDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия studentDao"+e.getMessage());
            }
            try {
                if (lessonDao!= null) {
                    lessonDao.close();
                }
            }catch (PersistException e) {
                System.err.println("Ошибка закрытия lessonDao"+e.getMessage());
            }
            try {
                if (markDao!= null) {
                    markDao.close();
                }
            }catch (PersistException e) {
                System.err.println("Ошибка закрытия markDao"+e.getMessage());
            }
        }
    }
}
