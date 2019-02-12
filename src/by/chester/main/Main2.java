package by.chester.main;
import by.chester.dao.*;
import by.chester.mySqlDAO.MySqlDaoFactory;
import by.chester.entities.*;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws PersistException {

        AddressDao addressDao = null;
        CategoryDao categoryDao = null;
        CustomerDao customerDao = null;
        OrderDao orderDao = null;
        ProductDao productDao = null;
        UserDao userDao = null;

        try {
            DaoFactory factory = new MySqlDaoFactory();
            addressDao = factory.getMySqlAddressDao();
            categoryDao = factory.getMySqlCategoryDao();
            customerDao = factory.getMySqlCustomerDao();
            orderDao = factory.getMySqlOrderDao();
            productDao = factory.getMySqlProductDao();
            userDao = factory.getMySqlUserDao();
//чтение всех
            List<Customer> customers = customerDao.getAll();
            for (Customer cstm : customers) {
                System.out.println(cstm.getId() + " " + cstm.getName() + " " + cstm.getSurname()
                        + " " + cstm.getPhoneNumber());
            }
            List<Address> addresses = addressDao.getAll();
            for (Address a : addresses) {
                System.out.println(a.getId() + " " + a.getCustomerId() + " " + a.getStreet() + " " + a.getHouseNumber()
                        + " " + a.getPorchNumber() + " " + a.getFloorNumber() + " " + a.getApartmentNumber());
            }
            List<Category> categories = categoryDao.getAll();
            for (Category ct : categories) {
                System.out.println(ct.getId() + " " + ct.getCategoryName());
            }
            List<Product> products = productDao.getAll();
            for (Product pr : products) {
                System.out.println(pr.getId() + " " + pr.getCategoryId() + " " + pr.getPrice() + " " + pr.getDescription());
            }
            List<User> users = userDao.getAll();
            for (User us : users) {
                System.out.println(us.getId() + " " + us.getLogin() + " " + us.getPassword() + " " + us.getIsAdmin());
            }
            List<Order> orders = orderDao.getAll();
            for (Order or : orders) {
                System.out.println(or.getId() + " " + or.getProductId() + " " + or.getAddressId() + " " + or.getNumber()
                        + " " + or.getDelivery() + " " + or.getOrderTime() + " " + or.getOrderStatus());
            }

//обновляем всех
            Student st4 = new Student();
            st4.setId(3);
            st4.setName("Virtor");
            st4.setSurname("Pipkin");
            st4.setBirthDate("19890101");
            st4.setEnterYear(2006);
            studentDao.update(st4);
////обновляем предмет
//            Lesson ls2 = new Lesson();
//            ls2.setId(2);
//            ls2.setLesson("Chemistry");
//            lessonDao.update(ls2);
////обновляем оценку
//            Mark mr2 = new Mark();
//            mr2.setId(3);
//            mr2.setMark(9);
//            markDao.update(mr2);
////добавляем студента
//            Student st1 = new Student();
//            st1.setName("Dmitrii");
//            st1.setSurname("Novoselov");
//            st1.setBirthDate("19861009");
//            st1.setEnterYear(2008);
//            studentDao.create(st1);
////добавляем предмет
//            Lesson ls1 = new Lesson();
//            ls1.setLesson("Informatics");
//            lessonDao.create(ls1);
////добавляем оценку
//            Mark mr1 = new Mark();
//            mr1.setStudentId(3);
//            mr1.setLessonId(2);
//            mr1.setMark(10);
//            markDao.create(mr1);
////удаляем студента
//            Student st5 = new Student();
//            st5.setId(13);
//            studentDao.delete(st5);
////удаляем оценку
//            Mark mr3 = new Mark();
//            mr3.setId(6);
//            markDao.delete(mr3);
////удаляем предмет
//            Lesson ls3 = new Lesson();
//            ls3.setId(7);
//            lessonDao.delete(ls3);
        } catch (PersistException e) {
            throw new PersistException("Ошибка Sql запроса в классе Main", e);
        } finally {
            try {
                if (addressDao != null) {
                    addressDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия studentDao" + e.getMessage());
            }
            try {
                if (categoryDao != null) {
                    categoryDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия lessonDao" + e.getMessage());
            }
            try {
                if (customerDao != null) {
                    customerDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия markDao" + e.getMessage());
            }
        }
    }
}
