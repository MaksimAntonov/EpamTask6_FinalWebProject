package by.antonov.webproject.model.dao.impl;

import static by.antonov.webproject.model.dao.DatabaseColumnName.COUNT;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_EMAIL;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_FIRST_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_ID;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_LAST_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_PHONE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_PSWD_HASH;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_REGISTRATION_DATE;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_ROLE_NAME;
import static by.antonov.webproject.model.dao.DatabaseColumnName.USER_STATUS_NAME;

import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Status;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.model.connection.ConnectionPool;
import by.antonov.webproject.model.dao.UserDao;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl implements UserDao {

  private static final Logger logger = LogManager.getLogger();

  private static final String SQL_FIND_ALL_USERS = """
      SELECT `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name`
      FROM `users_list`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`""";
  private static final String SQL_FIND_USER_BY_ID = """
      SELECT `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name`
      FROM `users_list`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE `users_list`.`user_id`=?""";
  private static final String SQL_FIND_USER_BY_EMAIL = """
      SELECT `users_list`.`user_id`, `users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, `users_list`.`user_phone`, `users_list`.`user_registration_date`, `users_role`.`role_name`, `users_status`.`status_name`
      FROM `users_list`
      JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`
      JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`
      WHERE `users_list`.`user_email`=?""";
  private static final String SQL_FIND_PASSWORD_HASH_BY_EMAIL = """
      SELECT `users_list`.`user_pswd_hash`
      FROM `users_list`
      WHERE `users_list`.`user_email`=?""";
  private static final String SQL_COUNT_BY_EMAIL = """
      SELECT COUNT(`user_email`) as `count`
      FROM `users_list`
      WHERE `users_list`.`user_email`=?""";
  private static final String SQL_INSERT_NEW_USER = "INSERT INTO `users_list` (`user_email`, `user_pswd_hash`," +
      " `user_pswd_salt`, `user_first_name`, `user_last_name`, `user_phone`, `user_role_id`, `user_status_id`) VALUES (?,?,?," +
      "?,?,?,?,?)";
  private static final String SQL_UPDATE_USER_NAME = "UPDATE IGNORE `users_list` SET `user_first_name`=?, `user_last_name`=? WHERE `user_id`=?";
  private static final String SQL_UPDATE_USER_PHONE = "UPDATE IGNORE `users_list` SET `user_phone`=? WHERE `user_id`=?";
  private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE IGNORE `users_list` SET `user_pswd_hash`=?, `user_pswd_salt`=? WHERE `user_id`=?";
  private static final String SQL_UPDATE_USER_STATUS = "UPDATE IGNORE `users_list` SET `user_status_id`=? WHERE `user_id`=?";
  private static final String SQL_FIND_STATUS_BY_USER_ID = "SELECT `users_status`.`status_name` FROM `users_status`, `users_list` WHERE `users_status`.`status_id`=`users_list`.`user_status_id` AND `users_list`.`user_id`=?";
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
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
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
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
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
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
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
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public Optional<Status> findStatusById(long userId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_STATUS_BY_USER_ID)) {
      statement.setLong(1, userId);
      ResultSet resultSet = statement.executeQuery();

      User.Status userStatus = null;
      while (resultSet.next()) {
        userStatus = User.Status.valueOf(resultSet.getString(USER_STATUS_NAME));
      }
      return Optional.ofNullable(userStatus);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean existRowsByEmail(String email)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_COUNT_BY_EMAIL)) {
      statement.setString(1, email);
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

  @Override
  public boolean insertUser(String email,
                            String passwordHash,
                            String passwordSalt,
                            String firstName,
                            String lastName,
                            String phone,
                            long userRoleId,
                            long userStatusId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_USER)) {
      statement.setString(1, email);
      statement.setString(2, passwordHash);
      statement.setString(3, passwordSalt);
      statement.setString(4, firstName);
      statement.setString(5, lastName);
      statement.setString(6, phone);
      statement.setLong(7, userRoleId);
      statement.setLong(8, userStatusId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      logger.info("SQL request error({}). {}", sqlException.getErrorCode(), sqlException.getMessage());
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean updateUserName(long userId, String firstName, String lastName)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_NAME)) {
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setLong(3, userId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean updateUserPhone(long userId, String phone)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PHONE)) {
      statement.setString(1, phone);
      statement.setLong(2, userId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean updateUserPassword(long userId, String passwordHash, String passwordSalt)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
      statement.setString(1, passwordHash);
      statement.setString(2, passwordSalt);
      statement.setLong(3, userId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      logger.info("SQL Error code: {}", sqlException.getErrorCode());
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }

  @Override
  public boolean updateUserStatus(long userId, long statusId)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATUS)) {
      statement.setLong(1, statusId);
      statement.setLong(2, userId);
      return (statement.executeUpdate() == 1);
    } catch (SQLException sqlException) {
      logger.error("SQL request error: {}", sqlException.getMessage());
      throw new DaoException("SQL request error. " + sqlException.getMessage(), sqlException);
    }
  }
}
