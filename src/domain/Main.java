package domain;
import daoFactory.DaoFactory;
import daoFactory.PersistException;
import mySql.MySqlDaoFactory;
import mySql.MySqlLessonDao;
import mySql.MySqlMarkDao;
import mySql.MySqlStudentDao;

public class Main {
    public static void main(String[] args) throws PersistException {

        MySqlMarkDao markDao = null;
        MySqlLessonDao lessonDao = null;
        MySqlStudentDao studentDao=null;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            studentDao = factory.getMySqlStudentDao();
            lessonDao = factory.getMySqlLessonDao();
            markDao = factory.getMySqlMarkDao();
        } catch (Exception e) {
            System.out.println("При работе с базой данных произошла ошибка.");
        }
//читаем всех студентов
        studentDao.getAll();
//получаем все предметы одного студента вместе с их оценками
        markDao.getAll();
//получаем все предметы
        lessonDao.getAll();
//обновляем студента
        Student st4 = new Student();
        st4.setId(3);
        st4.setName("Virtor");
        st4.setSurname("Pipkin");
        st4.setBirthDate("19990101");
        st4.setEnterYear(2005);
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
        st1.setEnterYear(2006);
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
        Student st5=new Student();
        st5.setId(13);
        studentDao.delete(st5);
//удаляем оценку
        Mark mr3=new Mark();
        mr3.setId(6);
        markDao.delete(mr3);
//удаляем предмет
        Lesson ls3=new Lesson();
        ls3.setId(7);
        lessonDao.delete(ls3);
    }
}
