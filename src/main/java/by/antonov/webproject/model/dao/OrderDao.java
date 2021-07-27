package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

/**
 * Interface for class with SQL requests to `orders_list`
 */
public interface OrderDao extends BaseDao<Long, Order> {

  /**
   * Find all orders by Shipper
   * @param shipperId ID of user
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Order> findAllByShipperId(long shipperId)
      throws DaoException;

  /**
   * Find all orders by Shipper
   * @param shipperId ID of user
   * @param limit maximum records, which should be in resultSet
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Order> findAllByShipperId(long shipperId, int limit)
      throws DaoException;

  /**
   * Find all orders by Carrier
   * @param carrierId ID of user
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Order> findAllActiveOrdersByCarrierId(long carrierId)
      throws DaoException;

  /**
   * Find all orders by Carrier
   * @param carrierId ID of user
   * @param limit maximum records, which should be in resultSet
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Order> findAllActiveOrdersByCarrierId(long carrierId, int limit)
      throws DaoException;

  /**
   * Change order status
   * @param orderId ID of order
   * @param orderStatus status value, which should be set
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean changeOrderStatusById(long orderId, Order.Status orderStatus)
      throws DaoException;

  /**
   * Insert new record in database
   * @param route route for order
   * @param details details for order
   * @param shipperId ID of user
   * @return TRUE if record(s) was inserted, FALSE if record(s) wasn't inserted
   * @throws DaoException in case of any SQL errors
   */
  boolean insertOrder(String route, String details, long shipperId)
      throws DaoException;

  /**
   * Set current timestamp for record
   * @param orderId ID of order
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean updateDateByOrderId(long orderId)
      throws DaoException;
}
