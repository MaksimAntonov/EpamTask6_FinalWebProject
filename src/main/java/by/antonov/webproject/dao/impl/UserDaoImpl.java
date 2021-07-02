package by.antonov.webproject.dao.impl;

import static by.antonov.webproject.dao.DatabaseColumnName.USER_FIRST_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_ID;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_LAST_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_EMAIL;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_PHONE;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_REGISTRATION_DATE;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_ROLE_NAME;
import static by.antonov.webproject.dao.DatabaseColumnName.USER_STATUS_NAME;

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
  private static final String SQL_FIND_ALL_USERS = "SELECT `users_list`.`user_id`, `users_list`.`user_first_name`, " +
      "`users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, " +
      "`users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `users_list` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`";
  private static final String SQL_FIND_USER_BY_ID = "SELECT `users_list`.`user_id`, `users_list`.`user_first_name`, " +
      "`users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, " +
      "`users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `users_list` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `users_list`.`user_id`=?";
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public List<User> findAll()
      throws DaoException {
    List<User> users = new ArrayList<>();
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
      while (resultSet.next()) {
        User.Builder builder = new User.Builder();

        builder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));

        users.add(builder.build());
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
      connection = ConnectionPool.getInstance().getConnection();
      statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        User.Builder builder = new User.Builder();

        user = builder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()))
               .build();
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
