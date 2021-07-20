package by.antonov.webproject.model.dao;

import by.antonov.webproject.model.dao.impl.OfferDaoImpl;
import by.antonov.webproject.model.dao.impl.OrderDaoImpl;
import by.antonov.webproject.model.dao.impl.UserDaoImpl;

public class DaoDefinition {
  private static DaoDefinition instance;
  private final OrderDao orderDao;
  private final OfferDao offerDao;
  private final UserDao userDao;

  private DaoDefinition() {
    this.orderDao = new OrderDaoImpl();
    this.offerDao = new OfferDaoImpl();
    this.userDao = new UserDaoImpl();
  }

  public static DaoDefinition getInstance() {
    if (instance == null) {
      instance = new DaoDefinition();
    }

    return instance;
  }

  public OrderDao getOrderDao() {
    return orderDao;
  }

  public OfferDao getOfferDao() {
    return offerDao;
  }

  public UserDao getUserDao() {
    return userDao;
  }
}
