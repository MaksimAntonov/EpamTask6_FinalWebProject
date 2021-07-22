package by.antonov.webproject.model.service.impl;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.model.dao.DaoDefinition;
import by.antonov.webproject.model.dao.OfferDao;
import by.antonov.webproject.model.dao.OrderDao;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OrderService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements OrderService {
  private static final Logger logger = LogManager.getLogger();
  private final OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();

  @Override
  public List<Order> getAllOrders()
      throws ServiceException {
    try {
      List<Order> orders = orderDao.findAll();

      for (Order order : orders) {
        List<Offer> offers = DaoDefinition.getInstance().getOfferDao().findAllByOrderId(order.getId());
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }

      return orders;
    } catch (DaoException daoException) {
      logger.error("findAllOrders > Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public List<Order> getAllOrdersByShipperId(long shipperId)
      throws ServiceException {
    try {
      List<Order> orders = orderDao.findAllByShipperId(shipperId);

      for (Order order : orders) {
        List<Offer> offers = DaoDefinition.getInstance().getOfferDao().findAllByOrderId(order.getId());
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }

      return orders;
    } catch (DaoException daoException) {
      logger.error("findAllOrders > Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean closeOrder(long orderId, long shipperId)
      throws ServiceException {
    try {
      OfferDao offerDao = DaoDefinition.getInstance().getOfferDao();
      boolean result = false;
      if (offerDao.countOffersByOrderId(orderId) > 0) {
        result = offerDao.denyOffersByOrderId(orderId);
      } else {
        result = true;
      }
      return (result && orderDao.closeOrderById(orderId));
    } catch (DaoException daoException) {
      logger.error("findAllOrders > Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }
}
