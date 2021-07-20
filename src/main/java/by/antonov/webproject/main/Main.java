package by.antonov.webproject.main;

import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.Localization;

public class Main {
  public static void main(String[] args)
      throws ServiceException {
    for (Localization localization : Localization.values()) {
      System.out.println(localization.getLanguageCode() + " - " + localization.getLanguageName());
    }

//    String email = "test@gmail.com";
//    String userPswd = "Admin1990";
//    String pswdHash = "$2a$10$xZ5Z1E4PMARoa1nPfty8Ue5wV1FrLb42jFiX9TbXyYZeBJtfiHEYq";
//    String pswdSalt = "$2a$10$xZ5Z1E4PMARoa1nPfty8Ue";
//
//    System.out.println(ServiceDefinition.getInstance().getUserService().checkLogin(email, userPswd));
//
//    System.out.println(PasswordHash.encryptPassword(userPswd, pswdSalt));
//    System.out.println(PasswordHash.check(userPswd, pswdHash));

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
