package by.antonov.webproject.model.dao.impl;

import static by.antonov.webproject.model.dao.DatabaseColumnName.COUNT;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_CREATE_DATE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_DETAILS;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_ID;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_ROUTE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_STATUS;
import static by.antonov.webproject.model.dao.DatabaseColumnName.ORDER_UPDATE_DATE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_EMAIL;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_FIRST_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_ID;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_LAST_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_PHONE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_REGISTRATION_DATE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_ROLE_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_STATUS_NAME;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.Order.Status;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.model.connection.ConnectionPool;
import by.antonov.webproject.model.dao.DatabaseColumnName;
import by.antonov.webproject.model.dao.OrderDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

  private static final String SQL_FIND_ALL_ORDERS = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
      `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
      `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
      `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
      `users_status`.`status_name`
      FROM `orders_list`
      JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      ORDER BY `orders_list`.`order_update_date`""";
  private static final String SQL_FIND_ALL_ACTIVE_ORDERS_FOR_CARRIER_ID = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
             `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
             `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
             `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
             `users_status`.`status_name`
      FROM `orders_list`
               JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
               JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
               JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE order_status='NEW'
         OR (order_status='FINISHED' AND order_id IN (SELECT offer_order_id FROM offers_list WHERE offer_carrier_id=?))
      ORDER BY order_update_date DESC""";
  private static final String SQL_FIND_ALL_ACTIVE_ORDERS_FOR_CARRIER_ID_WITH_LIMIT = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
             `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
             `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
             `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
             `users_status`.`status_name`
      FROM `orders_list`
               JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
               JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
               JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE order_status='NEW'
         OR (order_status='FINISHED' AND order_id IN (SELECT offer_order_id FROM offers_list WHERE offer_carrier_id=?))
      ORDER BY order_update_date DESC
      LIMIT ?, ?""";
  private static final String SQL_FIND_ORDER_BY_ID = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
      `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
      `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
      `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
      `users_status`.`status_name`
      FROM `orders_list`
      JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE `orders_list`.`order_id`=?""";
  private static final String SQL_FIND_ALL_ORDERS_BY_SHIPPER = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
      `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
      `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
      `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
      `users_status`.`status_name`
      FROM `orders_list`
      JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE `orders_list`.`order_shipper_id`=?
      ORDER BY `orders_list`.`order_update_date`""";
  private static final String SQL_FIND_ORDERS_BY_SHIPPER_WITH_LIMIT = """
      SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, `orders_list`.`order_route`,
      `orders_list`.`order_date`, `orders_list`.`order_update_date`, `orders_list`.`order_status`,
      `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`,
      `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`,
      `users_status`.`status_name`
      FROM `orders_list`
      JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE `orders_list`.`order_shipper_id`=?
      ORDER BY `orders_list`.`order_update_date` DESC
      LIMIT ?, ?""";
  private static final String SQL_UPDATE_STATUS_ORDER_BY_ID = """
      UPDATE `orders_list` SET `order_status`=?, `order_update_date`=current_timestamp
      WHERE `order_id`=? AND `order_status`='NEW'""";
  private static final String SQL_INSERT_NEW_ORDER = """
      INSERT INTO `orders_list` (`order_route`, `order_details`, `order_shipper_id`) VALUES (?, ?, ?)""";
  private static final String SQL_UPDATE_DATE_ORDER_BY_ID = """
      UPDATE `orders_list` SET `order_update_date`=current_timestamp WHERE `order_id`=?""";
  private static final String SQL_COUNT_ALL_ORDERS_BY_SHIPPER = """
      SELECT COUNT(`order_id`) as `count` FROM `orders_list` WHERE `order_shipper_id`=?""";
  private static final String SQL_COUNT_ALL_ORDERS_FOR_CARRIER = """
      SELECT COUNT(`order_id`) as `count` FROM `orders_list`
      WHERE order_status='NEW'
         OR (order_status='FINISHED' AND order_id IN (SELECT offer_order_id FROM offers_list WHERE offer_carrier_id=?))""";
  private static final String SQL_CHECK_ORDER_AUTHOR =
      "SELECT COUNT(`order_id`) as `count` FROM `orders_list` WHERE `order_id`=? AND `order_shipper_id`=?";
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public List<Order> findAll()
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS)) {
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public Optional<Order> findById(Long id)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      Order order = null;
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        order = orderBuilder.build();
      }
      return Optional.ofNullable(order);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllByShipperId(long shipperId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_SHIPPER)) {
      statement.setLong(1, shipperId);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllByShipperId(long shipperId, int limit)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_SHIPPER_WITH_LIMIT)) {
      statement.setLong(1, shipperId);
      statement.setInt(2, 0);
      statement.setInt(3, limit);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllByShipperId(long shipperId, int offset, int limit) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_SHIPPER_WITH_LIMIT)) {
      statement.setLong(1, shipperId);
      statement.setInt(2, offset);
      statement.setInt(3, limit);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllActiveOrdersByCarrierId(long carrierId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ACTIVE_ORDERS_FOR_CARRIER_ID)) {
      statement.setLong(1, carrierId);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllActiveOrdersByCarrierId(long carrierId, int limit)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ACTIVE_ORDERS_FOR_CARRIER_ID_WITH_LIMIT)) {
      statement.setLong(1, carrierId);
      statement.setInt(2, 0);
      statement.setInt(3, limit);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public List<Order> findAllActiveOrdersByCarrierId(long carrierId, int offset, int limit) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ACTIVE_ORDERS_FOR_CARRIER_ID_WITH_LIMIT)) {
      statement.setLong(1, carrierId);
      statement.setInt(2, offset);
      statement.setInt(3, limit);
      ResultSet resultSet = statement.executeQuery();
      List<Order> orders = new ArrayList<>();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setRoute(resultSet.getString(ORDER_ROUTE))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setCreateDate(LocalDateTime.parse(resultSet.getString(ORDER_CREATE_DATE), dtf))
                    .setUpdateDate(LocalDateTime.parse(resultSet.getString(ORDER_UPDATE_DATE), dtf))
                    .setOrderStatus(Order.Status.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
      return orders;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean changeOrderStatusById(long orderId, Status orderStatus)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS_ORDER_BY_ID)) {
      statement.setString(1, orderStatus.name());
      statement.setLong(2, orderId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean insertOrder(String route, String details, long shipperId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_ORDER)) {
      statement.setString(1, route);
      statement.setString(2, details);
      statement.setLong(3, shipperId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean updateDateByOrderId(long orderId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DATE_ORDER_BY_ID)) {
      statement.setLong(1, orderId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public int countOfAllOrdersByShipperId(long shipperID) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ALL_ORDERS_BY_SHIPPER)) {
      statement.setLong(1, shipperID);
      ResultSet resultSet = statement.executeQuery();
      int result = 0;
      while (resultSet.next()) {
        result = resultSet.getInt(DatabaseColumnName.COUNT);
      }
      return result;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public int countOfAllOrdersByCarrierId(long carrierId) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ALL_ORDERS_FOR_CARRIER)) {
      statement.setLong(1, carrierId);
      ResultSet resultSet = statement.executeQuery();
      int result = 0;
      while (resultSet.next()) {
        result = resultSet.getInt(DatabaseColumnName.COUNT);
      }
      return result;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean checkOrderAuthor(long orderId, long userId) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_CHECK_ORDER_AUTHOR)) {
      statement.setLong(1, orderId);
      statement.setLong(2, userId);
      ResultSet resultSet = statement.executeQuery();
      boolean result = false;
      while (resultSet.next()) {
        result = (resultSet.getInt(COUNT) >= 1);
      }
      return result;
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }
}
