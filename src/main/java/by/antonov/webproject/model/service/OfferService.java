package by.antonov.webproject.model.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;

/**
 * Interface for Service class for work with Offers
 */
public interface OfferService {

  /**
   * Get all orders for carrier with offers from him or without offer, if Order doesn't closed or finished
   * @param userId ID of User
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getOrdersForCarrier(long userId)
      throws ServiceException;

  /**
   * Get all orders for carrier with offers from him or without offer, if Order doesn't closed or finished
   * @param userId ID of User
   * @param limit maximum records, which should be in resultSet
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getOrdersForCarrier(long userId, int limit)
      throws ServiceException;

  /**
   * Get all orders for carrier with offers from him or without offer, if Order doesn't closed or finished
   * @param userId ID of User
   * @param page current page
   * @param limit maximum records, which should be in resultSet
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getOrdersForCarrier(long userId, int page, int limit)
      throws ServiceException;

  /**
   * Make new offer
   * @param price Offer price
   * @param carrierId ID of User
   * @param orderId ID of Order
   * @return TRUE if record(s) was inserted, FALSE if record(s) wasn't inserted
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean makeOffer(int price, long carrierId, long orderId)
      throws ServiceException;

  /**
   * Cancel offer
   * @param offerId ID of offer
   * @return TRUE if record(s) was updated, FALSE if record(s) can't be updated
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean cancelOffer(long offerId)
      throws ServiceException;
}
