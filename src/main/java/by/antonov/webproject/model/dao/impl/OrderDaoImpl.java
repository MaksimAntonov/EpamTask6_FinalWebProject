package by.antonov.webproject.model.dao.impl;

import static by.antonov.webproject.model.dao.DatabaseColumnName.*;

import by.antonov.webproject.model.connection.ConnectionPool;
import by.antonov.webproject.model.dao.OrderDao;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
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
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`""";
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
      WHERE `orders_list`.`order_shipper_id`=?""";
  private final String SQL_CLOSE_ORDER_BY_ID = """
      UPDATE `orders_list` SET `order_status`='CLOSED' WHERE `order_id`=?""";
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
  public List<Order> findAllByShipperId(Long shipperId)
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
  public boolean closeOrderById(long orderId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_CLOSE_ORDER_BY_ID)) {
      statement.setLong(1, orderId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }
}
