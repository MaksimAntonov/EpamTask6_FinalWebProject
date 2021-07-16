package by.antonov.webproject.service.impl;

import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements OrderService {
  private static final Logger logger = LogManager.getLogger();
  private final OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();

  @Override
  public List<Order> findAllOrders()
      throws ServiceException {
    List<Order> orders = new ArrayList<>();
    try {
      orders = orderDao.findAll();
    } catch (DaoException daoException) {
      logger.error("findAllOrders > Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database", daoException);
    }
    return orders;
  }
}
