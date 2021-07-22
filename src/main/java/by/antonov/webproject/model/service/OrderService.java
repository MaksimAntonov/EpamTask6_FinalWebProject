package by.antonov.webproject.model.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;

public interface OrderService {
  List<Order> getAllOrders() throws ServiceException;
  List<Order> getAllOrdersByShipperId(long shipperId) throws ServiceException;
  boolean closeOrder(long orderId, long shipperId) throws ServiceException;
}
