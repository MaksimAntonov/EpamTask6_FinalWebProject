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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
  private static final String SQL_FIND_ALL_ORDERS = "SELECT `orders_list`.`id`, `orders_list`.`details`, " +
      "`orders_list`.`ready_date`, `orders_list`.`end_date`, `users_list`.`login` " +
      "FROM `orders_list` " +
      "JOIN `users_list` ON `users_list`.`id` = `shipper_id`";
  private static final String SQL_FIND_ALL_ORDERS_BY_ID = "SELECT * FROM `orders_list` WHERE `id`=?";
  private static final String SQL_FIND_ALL_ORDERS_BY_SHIPPER = "SELECT * FROM `orders_list` WHERE `shipper_id`=?";
  Connection connection;

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
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    try {
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
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
      throw new DaoException("Database error", e);
    } finally {
      close(statement);
      close(connection);
    }

    return orders;
  }

  @Override
  public List<Order> findAllById(Long id) {
    return null;
  }

  @Override
  public List<Order> findAllByShipperId(Long id) {
    return null;
  }
}
