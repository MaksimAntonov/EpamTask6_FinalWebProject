package by.antonov.webproject.model.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for Service class for work with Orders
 */
public interface OrderService {

  /**
   * Get all orders
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getAllOrders()
      throws ServiceException;

  /**
   * Get all orders for current User
   * @param shipperId ID of User
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getAllOrdersByShipperId(long shipperId)
      throws ServiceException;

  /**
   * Get all orders for current User
   * @param shipperId ID of User
   * @param limit maximum records
   * @return List of Orders
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<Order> getAllOrdersByShipperId(long shipperId, int limit)
      throws ServiceException;

  /**
   * Get order
   * @param orderId ID of order
   * @return Optional with Order
   * @throws ServiceException in case of error(s) from Service implementation
   */
  Optional<Order> getOrderById(long orderId)
      throws ServiceException;

  /**
   * Close order
   * @param orderId ID of Order
   * @return TRUE if order was closed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean closeOrder(long orderId)
      throws ServiceException;

  /**
   * Accept offer and change Order status to FINISHED
   * @param offerId ID of Offer
   * @param orderId ID of Order
   * @return TRUE if offer was accepted and Order status was changed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean acceptOffer(long offerId, long orderId)
      throws ServiceException;

  /**
   * Decline offer
   * @param offerId ID of Offer
   * @return TRUE if offer was declined
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean declineOffer(long offerId)
      throws ServiceException;

  /**
   * Create new order
   * @param router Route
   * @param details Order details
   * @param shipperId ID of User
   * @return TRUE if Order was created
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean createOrder(String router, String details, long shipperId)
      throws ServiceException;
}
