package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OrderDao extends BaseDao<Long, Order>{
  List<Order> findAllByShipperId(Long shipperId)
      throws DaoException;
  boolean changeOrderStatusById(long orderId, Order.Status orderStatus)
      throws DaoException;
  boolean insertOrder(String route, String details, long shipperId) throws DaoException;
}
