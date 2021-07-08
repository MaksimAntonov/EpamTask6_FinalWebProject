package by.antonov.webproject.main;

import by.antonov.webproject.dao.OfferDao;
import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.dao.impl.OfferDaoImpl;
import by.antonov.webproject.dao.impl.OrderDaoImpl;
import by.antonov.webproject.dao.impl.UserDaoImpl;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.util.PasswordHash;

public class Main {
  public static void main(String[] args)
      throws ServiceException {
    String email = "test@gmail.com";
    String userPswd = "Admin1990";
    String pswdHash = "$2a$10$xZ5Z1E4PMARoa1nPfty8Ue5wV1FrLb42jFiX9TbXyYZeBJtfiHEYq";
    String pswdSalt = "$2a$10$xZ5Z1E4PMARoa1nPfty8Ue";

    System.out.println(ServiceDefinition.getInstance().getUserService().checkLogin(email, userPswd));

    System.out.println(PasswordHash.encryptPassword(userPswd, pswdSalt));
    System.out.println(PasswordHash.check(userPswd, pswdHash));

//    OrderDao orderDao = new OrderDaoImpl();
//    UserDao userDao = new UserDaoImpl();
//    OfferDao offerDao = new OfferDaoImpl();
//
//    try {
//      System.out.println(orderDao.findAll() + "\r\n");
//      System.out.println(orderDao.findById((long) 2) + "\r\n");
//
//      System.out.println(offerDao.findAll() + "\r\n");
//
//      System.out.println(userDao.findAll() + "\r\n");
//      System.out.println(userDao.findById((long) 1) + "\r\n");
//    } catch (DaoException e) {
//      e.printStackTrace();
//    }
  }
}
