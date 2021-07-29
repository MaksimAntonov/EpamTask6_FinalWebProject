package by.antonov.webproject.model.service.impl;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.dao.DaoDefinition;
import by.antonov.webproject.model.dao.OfferDao;
import by.antonov.webproject.model.dao.OrderDao;
import by.antonov.webproject.model.service.OfferService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfferServiceImpl implements OfferService {

  private static final Logger logger = LogManager.getLogger();
  private final OfferDao offerDao = DaoDefinition.getInstance().getOfferDao();

  @Override
  public List<Order> getOrdersForCarrier(long userId)
      throws ServiceException {
    try {
      OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();
      List<Order> orders = orderDao.findAllActiveOrdersByCarrierId(userId);

      for (Order order : orders) {
        List<Offer> offers = offerDao.findAllByOrderIdAndCarrierId(order.getId(), userId);
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }

      return orders;
    } catch (DaoException daoException) {
      logger.error("Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public List<Order> getOrdersForCarrier(long userId, int limit)
      throws ServiceException {
    try {
      OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();
      List<Order> orders = orderDao.findAllActiveOrdersByCarrierId(userId, limit);

      for (Order order : orders) {
        List<Offer> offers = offerDao.findAllByOrderIdAndCarrierId(order.getId(), userId);
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }

      return orders;
    } catch (DaoException daoException) {
      logger.error("Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public List<Order> getOrdersForCarrier(long userId, int page, int limit) throws ServiceException {
    try {
      page = page - 1;

      OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();
      List<Order> orders = orderDao.findAllActiveOrdersByCarrierId(userId, page, limit);

      for (Order order : orders) {
        List<Offer> offers = offerDao.findAllByOrderIdAndCarrierId(order.getId(), userId);
        Optional<Offer> bestOfferOpt = offers.stream().min(Comparator.comparingDouble(Offer::getPrice));
        bestOfferOpt.ifPresent(order::setBestOffer);
        order.setOffers(offers);
      }

      return orders;
    } catch (DaoException daoException) {
      logger.error("Can not read data from database: {}", daoException.getMessage());
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean makeOffer(int price, long carrierId, long orderId)
      throws ServiceException {
    boolean result = false;
    if (price >= 1) {
      try {
        if (offerDao.insertOffer(price, carrierId, orderId)) {
          OrderDao orderDao = DaoDefinition.getInstance().getOrderDao();
          result = orderDao.updateDateByOrderId(orderId);
        }
      } catch (DaoException daoException) {
        throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
      }
    }
    return result;
  }

  @Override
  public boolean cancelOffer(long offerId)
      throws ServiceException {
    try {
      return offerDao.declineOfferById(offerId);
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }
}
