package by.antonov.webproject.service;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;

public interface OrderService {
  List<Order> findAllOrders() throws ServiceException;
}
