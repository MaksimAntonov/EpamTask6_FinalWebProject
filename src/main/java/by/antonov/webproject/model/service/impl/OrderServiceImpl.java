package by.antonov.webproject.model.service.impl;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.Order.Status;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.dao.DaoDefinition;
import by.antonov.webproject.model.dao.OfferDao;
import by.antonov.webproject.model.dao.OrderDao;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.util.Validator;
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
  public List<Order> getAllOrdersByShipperId(long shipperId, int limit)
      throws ServiceException {
    try {
      List<Order> orders = orderDao.findAllByShipperId(shipperId, limit);

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
  public Optional<Order> getOrderById(long orderId)
      throws ServiceException {
    try {
      Optional<Order> orderOpt = orderDao.findById(orderId);
      Order order = null;
      if (orderOpt.isPresent()) {
        order = orderOpt.get();

        List<Offer> offers = DaoDefinition.getInstance().getOfferDao().findAllByOrderId(order.getId());
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }
      return Optional.ofNullable(order);
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }

  }

  @Override
  public boolean closeOrder(long orderId)
      throws ServiceException {
    try {
      OfferDao offerDao = DaoDefinition.getInstance().getOfferDao();
      boolean result;
      if (offerDao.countOffersByOrderId(orderId) > 0) {
        result = offerDao.declineOffersByOrderId(orderId);
      } else {
        result = true;
      }
      return (result && orderDao.changeOrderStatusById(orderId, Status.CLOSED));
    } catch (DaoException daoException) {
      logger.error("Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean acceptOffer(long offerId, long orderId)
      throws ServiceException {
    try {
      OfferDao offerDao = DaoDefinition.getInstance().getOfferDao();
      boolean result;
      if (offerDao.countOffersByOrderId(orderId) > 0) {
        result = (offerDao.declineOffersByOrderId(orderId, offerId) && offerDao.acceptOfferById(offerId));
      } else {
        result = true;
      }
      return (result && orderDao.changeOrderStatusById(orderId, Status.FINISHED));
    } catch (DaoException daoException) {
      logger.error("Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean declineOffer(long offerId)
      throws ServiceException {
    try {
      OfferDao offerDao = DaoDefinition.getInstance().getOfferDao();
      return offerDao.declineOfferById(offerId);
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean createOrder(String router, String details, long shipperId)
      throws ServiceException {
    boolean result = false;
    if (Validator.checkRouteText(router) && Validator.checkText(details)) {
      try {
        result = orderDao.insertOrder(router, details, shipperId);
      } catch (DaoException daoException) {
        throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
      }
    }
    return result;
  }
}
