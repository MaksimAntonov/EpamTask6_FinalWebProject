package by.antonov.webproject.dao.impl;

import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_ID;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_DETAILS;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_READY_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.ORDER_END_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_LOGIN;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.dao.OrderDao;
import by.antonov.webproject.entity.Order;
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
  private static final String SQL_FIND_ALL_ORDERS = "SELECT `orders_list`.`id`, `orders_list`.`details`, " +
      "`orders_list`.`ready_date`, `orders_list`.`end_date`, `users_list`.`login` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`id` = `shipper_id`";
  private static final String SQL_FIND_ORDER_BY_ID = "SELECT `orders_list`.`id`, `orders_list`.`details`, " +
      "`orders_list`.`ready_date`, `orders_list`.`end_date`, `users_list`.`login` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`id` = `shipper_id` " +
      "WHERE `orders_list`.`id`=?";
  private static final String SQL_FIND_ALL_ORDERS_BY_SHIPPER = "SELECT `orders_list`.`id`, `orders_list`.`details`, " +
      "`orders_list`.`ready_date`, `orders_list`.`end_date`, `users_list`.`login` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`id` = `shipper_id` " +
      "WHERE `orders_list`.`shipper_id`=?";
  private Connection connection;
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public OrderDaoImpl() {}

  public OrderDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Order> findAll()
      throws DaoException {
    List<Order> orders = new ArrayList<>();
    Connection connection = null;
    Statement statement = null;
    try {
      connection = (this.connection == null) ? ConnectionPool.getInstance().getConnection() : this.connection;
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
      while (resultSet.next()) {
        Order order = new Order();
        order.setId(resultSet.getLong(ORDER_ID));
        order.setDetails(resultSet.getString(ORDER_DETAILS));
        order.setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf));
        order.setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf));

        // TODO How implement User, if it must be object? Create User here?
        order.setUser(resultSet.getString(USER_LOGIN));

        orders.add(order);
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
      connection = (this.connection == null) ? ConnectionPool.getInstance().getConnection() : this.connection;
      statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        order = new Order();

        order.setId(resultSet.getLong(ORDER_ID));
        order.setDetails(resultSet.getString(ORDER_DETAILS));
        order.setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf));
        order.setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf));
        order.setUser(resultSet.getString(USER_LOGIN));
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
      connection = (this.connection == null) ? ConnectionPool.getInstance().getConnection() : this.connection;
      statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_SHIPPER);
      statement.setLong(1, shipperId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Order order = new Order();
        order.setId(resultSet.getLong(ORDER_ID));
        order.setDetails(resultSet.getString(ORDER_DETAILS));
        order.setReadyDate(LocalDateTime.parse(resultSet.getString(ORDER_READY_DATE), dtf));
        order.setEndDate(LocalDateTime.parse(resultSet.getString(ORDER_END_DATE), dtf));
        order.setUser(resultSet.getString(USER_LOGIN));

        orders.add(order);
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
