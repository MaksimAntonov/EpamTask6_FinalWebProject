package by.antonov.webproject.model.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;

public interface OfferService {

  List<Order> getOrdersForCarrier(long userId)
      throws ServiceException;

  List<Order> getOrdersForCarrier(long userId, int limit)
      throws ServiceException;

  boolean makeOffer(int price, long carrierId, long orderId)
      throws ServiceException;

  boolean cancelOffer(long offerId)
      throws ServiceException;
}
