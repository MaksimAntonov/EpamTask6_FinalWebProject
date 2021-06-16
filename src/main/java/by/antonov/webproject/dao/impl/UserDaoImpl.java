package by.antonov.webproject.dao.impl;

import static by.antonov.webproject.dao.DatabaseColumnName.USER_ID;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_LOGIN;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_EMAIL;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_REGISTRATION_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.ROLE_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.STATUS_NAME;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.dao.UserDao;
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

public class UserDaoImpl implements UserDao {
  private static final String SQL_FIND_ALL_USERS = "SELECT `users_list`.`id`, `users_list`.`login`, " +
      "`users_list`.`email`, `users_list`.`registration_date`, `users_role`.`role`, `users_status`.`status` " +
      "FROM `users_list` " +
      "JOIN `users_role` ON `users_role`.`id` = `role_id` " +
      "JOIN `users_status` ON `users_status`.`id` = `status_id`";
  private static final String SQL_FIND_USER_BY_ID = "SELECT `users_list`.`id`, `users_list`.`login`, " +
      "`users_list`.`email`, `users_list`.`registration_date`, `users_role`.`role`, `users_status`.`status` " +
      "FROM `users_list` " +
      "JOIN `users_role` ON `users_role`.`id` = `role_id` " +
      "JOIN `users_status` ON `users_status`.`id` = `status_id`" +
      "WHERE `users_list`.`id`=?";
  private Connection connection;
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public UserDaoImpl() {}

  public UserDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<User> findAll()
      throws DaoException {
    List<User> users = new ArrayList<>();
    Connection connection = null;
    Statement statement = null;
    try {
      connection = (this.connection == null) ? ConnectionPool.getInstance().getConnection() : this.connection;
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
      while (resultSet.next()) {
        User user = new User();

        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setEmail(resultSet.getString(USER_EMAIL));
        user.setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf));
        user.setUserRole(UserRole.valueOf(resultSet.getString(ROLE_NAME).toUpperCase()));
        user.setUserStatus(UserStatus.valueOf(resultSet.getString(STATUS_NAME).toUpperCase()));

        users.add(user);
      }
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage());
    } finally {
      close(statement);
      close(connection);
    }

    return users;
  }

  @Override
  public Optional<User> findById(Long id)
      throws DaoException {
    User user = null;
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = (this.connection == null) ? ConnectionPool.getInstance().getConnection() : this.connection;
      statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        user = new User();

        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setEmail(resultSet.getString(USER_EMAIL));
        user.setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf));
        user.setUserRole(UserRole.valueOf(resultSet.getString(ROLE_NAME).toUpperCase()));
        user.setUserStatus(UserStatus.valueOf(resultSet.getString(STATUS_NAME).toUpperCase()));
      }
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage());
    } finally {
      close(statement);
      close(connection);
    }

    return Optional.ofNullable(user);
  }
}
