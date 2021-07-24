package by.antonov.webproject.model.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<Order> getAllOrders() throws ServiceException;
  List<Order> getAllOrdersByShipperId(long shipperId) throws ServiceException;
  List<Order> getAllOrdersByShipperId(long shipperId, int limit) throws ServiceException;
  Optional<Order> getOrderById(long orderId) throws ServiceException;
  boolean closeOrder(long orderId) throws ServiceException;
  boolean acceptOffer(long offerId, long orderId) throws ServiceException;
  boolean declineOffer(long offerId) throws ServiceException;
  boolean createOrder(String router, String details, long shipperId) throws ServiceException;
}
