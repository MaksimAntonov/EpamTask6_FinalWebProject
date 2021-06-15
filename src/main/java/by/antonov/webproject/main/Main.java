package by.antonov.webproject.main;

import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.dao.impl.OrderDaoImpl;
import by.antonov.webproject.exception.DaoException;

public class Main {
  public static void main(String[] args) {
    OrderDao dao = new OrderDaoImpl();

    try {
      System.out.println(dao.findAll());
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }
}
