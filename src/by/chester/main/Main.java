package by.chester.main;
import by.chester.dao.*;
import by.chester.mySqlDAO.MySqlDaoFactory;
import by.chester.entities.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws PersistException {

        AddressDao addressDao = null;
        CategoryDao categoryDao = null;
        CustomerDao customerDao = null;
        OrderDao orderDao = null;
        ProductDao productDao = null;
        UserDao userDao = null;

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

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
//обновление всех
User us =new User();
            us.setId(1);
            us.setLogin("Virtor");
            us.setPassword("Pipkin");
            us.setIsAdmin(0);
            userDao.update(us);
Product pr = new Product();
            pr.setId(1);
            pr.setCategoryId(1);
            pr.setPrice(30);
            pr.setDescription("Sushi with tuna");
            productDao.update(pr);
Customer cs = new Customer();
            cs.setId(1);
            cs.setName("Vitja");
            cs.setSurname("Vitek");
            cs.setPhoneNumber(292661142);
            customerDao.update(cs);
Category cat = new Category();
            cat.setId(1);
            cat.setCategoryName("Rolli");
            categoryDao.update(cat);
Order or = new Order();
            or.setId(1);
            or.setNumber(1);
            or.setDelivery("not required");
            or.setOrderTime("2019-01-01 10:00:00");
            or.setOrderStatus("ready");
            orderDao.update(or);
Address addr = new Address();
            addr.setId(1);
            addr.setStreet("Pr. Pushkina");
            addr.setHouseNumber(15);
            addr.setPorchNumber(15);
            addr.setFloorNumber(15);
            addr.setApartmentNumber(15);
            addressDao.update(addr);
//добавление всех
User us1 =new User();
            us1.setLogin("Virtor");
            us1.setPassword("Pipkin");
            us1.setIsAdmin(0);
            userDao.create(us1);
Product pr1 = new Product();
            pr1.setCategoryId(1);
            pr1.setPrice(35);
            pr1.setDescription("Sushi with salmon");
            productDao.create(pr1);
Customer cs1 = new Customer();
            cs1.setName("Diana");
            cs1.setSurname("Velichko");
            cs1.setPhoneNumber(292363362);
            customerDao.create(cs1);
Category cat1 = new Category();
            cat1.setCategoryName("Seti");
            categoryDao.create(cat1);
Order or1 = new Order();
            or1.setProductId(1);
            or1.setAddressId(1);
            or1.setNumber(1);
            or1.setDelivery("required");
            or1.setOrderTime(currentTime);
            or1.setOrderStatus("ready");
            orderDao.create(or1);
Address addr1 = new Address();
            addr1.setCustomerId(5);
            addr1.setStreet("Pr. Dzerzinskogo");
            addr1.setHouseNumber(17);
            addr1.setPorchNumber(17);
            addr1.setFloorNumber(17);
            addr1.setApartmentNumber(177);
            addressDao.create(addr1);
//удаляем всех
User us2 =new User();
            us2.setId(2);
            userDao.delete(us2);
Product pr2 = new Product();
            pr2.setId(2);
            productDao.delete(pr2);
Customer cs2 = new Customer();
            cs2.setId(2);
            customerDao.delete(cs2);
Category cat2 = new Category();
            cat2.setId(2);
            categoryDao.delete(cat2);
Order or2 = new Order();
            or2.setId(9);
            orderDao.delete(or2);
Address addr2 = new Address();
            addr2.setId(5);
            addressDao.delete(addr2);
        } catch (PersistException e) {
            throw new PersistException("Ошибка Sql запроса в классе Main", e);
        } finally {
            try {
                if (addressDao != null) {
                    addressDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия addressDao" + e.getMessage());
            }
            try {
                if (orderDao != null) {
                    orderDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия orderDao" + e.getMessage());
            }
            try {
                if (categoryDao != null) {
                    categoryDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия categoryDao" + e.getMessage());
            }
            try {
                if (customerDao != null) {
                    customerDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия customerDao" + e.getMessage());
            }
            try {
                if (productDao != null) {
                    productDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия productDao" + e.getMessage());
            }
            try {
                if (userDao != null) {
                    userDao.close();
                }
            } catch (PersistException e) {
                System.err.println("Ошибка закрытия userDao" + e.getMessage());
            }
        }
    }
}
