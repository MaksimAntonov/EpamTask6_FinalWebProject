package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OrderDao extends BaseDao<Long, Order>{
  List<Order> findAllByShipperId(long shipperId)
      throws DaoException;
  List<Order> findAllByShipperId(long shipperId, int limit)
      throws DaoException;
  List<Order> findAllActiveOrdersByCarrierId(long carrierId) throws DaoException;
  List<Order> findAllActiveOrdersByCarrierId(long carrierId, int limit) throws DaoException;
  boolean changeOrderStatusById(long orderId, Order.Status orderStatus)
      throws DaoException;
  boolean insertOrder(String route, String details, long shipperId) throws DaoException;
  boolean updateDateByOrderId(long orderId) throws DaoException;
}
