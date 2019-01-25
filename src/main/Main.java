package main;
import mySql.MySqlDaoFactory;

public class Main {
    public static void main(String[] args) {
        new MySqlDaoFactory();
        MySqlDaoFactory.getConnection();
    }
}
