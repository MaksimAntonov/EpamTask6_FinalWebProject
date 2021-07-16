package by.antonov.webproject.dao.impl;

import static by.antonov.webproject.dao.DatabaseColumnName.*;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.dao.UserDao;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
  private static final String SQL_FIND_USER_BY_EMAIL = "SELECT `users_list`.`user_id`, `users_list`" +
      ".`user_first_name`, " +
      "`users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, " +
      "`users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `users_list` " +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id` " +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `users_list`.`user_email`=?";
  private static final String SQL_FIND_PASSWORD_HASH_BY_EMAIL = "SELECT `users_list`.`user_pswd_hash` " +
      "FROM `users_list` " +
      "WHERE `users_list`.`user_email`=?";
  private static final String SQL_INSERT_NEW_USER = "INSERT IGNORE INTO `users_list` (`user_email`, `user_pswd_hash`, " +
      "`user_pswd_salt`, `user_first_name`, `user_last_name`, `user_phone`, `user_role_id`) VALUES (?,?,?,?,?,?,?)";
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public List<User> findAll()
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS)) {
      List<User> users = new ArrayList<>();
      while (resultSet.next()) {
        User.Builder builder = new User.Builder();

        builder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));

        users.add(builder.build());
      }
      return users;
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage());
    }
  }

  @Override
  public Optional<User> findById(Long id)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      User user = null;
      while (resultSet.next()) {
        User.Builder builder = new User.Builder();

        user = builder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()))
               .build();
      }
      return Optional.ofNullable(user);
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage(), e);
    }
  }

  @Override
  public Optional<User> findUserByEmail(String email)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();
      User user = null;
      while (resultSet.next()) {
        User.Builder builder = new User.Builder();

        user = builder.setId(resultSet.getLong(USER_ID))
                      .setEmail(resultSet.getString(USER_EMAIL))
                      .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                      .setLastName(resultSet.getString(USER_LAST_NAME))
                      .setFirstName(resultSet.getString(USER_FIRST_NAME))
                      .setPhone(resultSet.getString(USER_PHONE))
                      .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                      .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()))
                      .build();
      }
      return Optional.ofNullable(user);
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage(), e);
    }
  }

  @Override
  public Optional<String> findPasswordHashByEmail(String email)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_PASSWORD_HASH_BY_EMAIL)) {
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();
      String result = null;
      while (resultSet.next()) {
        result = resultSet.getString(USER_PSWD_HASH);
      }
      return Optional.ofNullable(result);
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage(), e);
    }
  }

  @Override
  public boolean insertUser(String email,
                            String passwordHash,
                            String passwordSalt,
                            String firstName,
                            String lastName,
                            String phone,
                            long userRole)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_USER)) {
      statement.setString(1, email);
      statement.setString(2, passwordHash);
      statement.setString(3, passwordSalt);
      statement.setString(4, firstName);
      statement.setString(5, lastName);
      statement.setString(6, phone);
      statement.setLong(7, userRole);
      return  (statement.executeUpdate() == 1);
    } catch (SQLException e) {
      throw new DaoException("SQL request error. " + e.getMessage(), e);
    }
  }
}
