package by.antonov.webproject.main;

import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.dao.impl.OrderDaoImpl;
import by.antonov.webproject.dao.impl.UserDaoImpl;
import by.antonov.webproject.exception.DaoException;

public class Main {
  public static void main(String[] args) {
    OrderDao orderDao = new OrderDaoImpl();
    UserDao userDao = new UserDaoImpl();

    try {
      System.out.println(orderDao.findAll() + "\r\n");
      System.out.println(orderDao.findById((long) 2) + "\r\n");

      System.out.println(userDao.findAll() + "\r\n");
      userDao.findById((long) 1).ifPresent(System.out::println);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }
}
