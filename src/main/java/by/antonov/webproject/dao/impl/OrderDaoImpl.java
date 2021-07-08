package by.antonov.webproject.dao.impl;

import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_ID;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_DETAILS;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_READY_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_END_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_STATUS;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_ROLE_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_STATUS_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_EMAIL;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_FIRST_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_ID;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_LAST_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_PHONE;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_REGISTRATION_DATE;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.OrderStatus;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.UserRole;
import by.antonov.webproject.entity.UserStatus;
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
  private static final String SQL_FIND_ALL_ORDERS = "SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, " +
      "`orders_list`.`order_ready_date`, `orders_list`.`order_end_date`, `orders_list`.`order_status`, " +
      "`users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, " +
      "`users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`";
  private static final String SQL_FIND_ORDER_BY_ID = "SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, " +
      "`orders_list`.`order_ready_date`, `orders_list`.`order_end_date`, `orders_list`.`order_status`, " +
      "`users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, " +
      "`users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `orders_list`.`order_id`=?";
  private static final String SQL_FIND_ALL_ORDERS_BY_SHIPPER = "SELECT `orders_list`.`order_id`, `orders_list`.`order_details`, " +
      "`orders_list`.`order_ready_date`, `orders_list`.`order_end_date`, `orders_list`.`order_status`, " +
      "`users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, " +
      "`users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`user_id` = `orders_list`.`order_shipper_id` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `orders_list`.`order_shipper_id`=?";
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public List<Order> findAll()
      throws DaoException {
    List<Order> orders = new ArrayList<>();
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf))
                    .setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf))
                    .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage());
    } finally {
      close(statement);
      close(connection);
    }

    return orders;
  }

  @Override
  public Optional<Order> findById(Long id)
      throws DaoException {
    Order order = null;
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf))
                    .setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf))
                    .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        order = orderBuilder.build();
      }
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage());
    } finally {
      close(statement);
      close(connection);
    }

    return Optional.ofNullable(order);
  }

  @Override
  public List<Order> findAllByShipperId(Long shipperId)
      throws DaoException {
    List<Order> orders = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_SHIPPER);
      statement.setLong(1, shipperId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Order.Builder orderBuilder = new Order.Builder();
        orderBuilder.setId(resultSet.getLong(ORDER_ID))
                    .setDetails(resultSet.getString(ORDER_DETAILS))
                    .setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf))
                    .setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf))
                    .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        orderBuilder.setUser(userBuilder.build());

        orders.add(orderBuilder.build());
      }
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage());
    } finally {
      close(statement);
      close(connection);
    }

    return orders;
  }
}
