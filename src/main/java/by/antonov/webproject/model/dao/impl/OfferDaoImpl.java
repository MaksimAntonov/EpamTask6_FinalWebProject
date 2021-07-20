package by.antonov.webproject.model.dao.impl;

import static by.antonov.webproject.model.dao.DatabaseColumnName.*;

import by.antonov.webproject.model.connection.ConnectionPool;
import by.antonov.webproject.model.dao.OfferDao;
import by.antonov.webproject.entity.Offer;
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

public class OfferDaoImpl implements OfferDao {
  private static final String SQL_FIND_ALL_OFFERS = "SELECT `offers_list`.`offer_id`, `offers_list`.`offer_price`, " +
      "`offers_list`.`offer_date`, `offers_list`.`offer_status`, `users_list`.`user_id`, "+
      "`users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, "+
      "`users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `offers_list`" +
      "JOIN `users_list` ON `users_list`.`user_id` = `offers_list`.`offer_carrier_id`" +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`" +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`";
  private static final String SQL_FIND_OFFER_BY_ID = "SELECT `offers_list`.`offer_id`, `offers_list`.`offer_price`, " +
      "`offers_list`.`offer_date`, `offers_list`.`offer_status`, `users_list`.`user_id`, "+
      "`users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, "+
      "`users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `offers_list`" +
      "JOIN `users_list` ON `users_list`.`user_id` = `offers_list`.`offer_carrier_id`" +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`" +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `offers_list`.`offer_id`=?";
  private static final String SQL_FIND_ALL_OFFERS_BY_CARRIER = "SELECT `offers_list`.`offer_id`, `offers_list`.`offer_price`, " +
      "`offers_list`.`offer_date`, `offers_list`.`offer_status`, `users_list`.`user_id`, "+
      "`users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, "+
      "`users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `offers_list`" +
      "JOIN `users_list` ON `users_list`.`user_id` = `offers_list`.`offer_carrier_id`" +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`" +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `offers_list`.`offer_carrier_id`=?";
  private static final String SQL_FIND_ALL_OFFERS_BY_ORDER = "SELECT `offers_list`.`offer_id`, `offers_list`.`offer_price`, " +
      "`offers_list`.`offer_date`, `offers_list`.`offer_status`, `users_list`.`user_id`, "+
      "`users_list`.`user_first_name`, `users_list`.`user_last_name`, `users_list`.`user_email`, "+
      "`users_list`.`user_phone`, `users_list`.`user_registration_date`, " +
      "`users_role`.`role_name`, `users_status`.`status_name` " +
      "FROM `offers_list`" +
      "JOIN `users_list` ON `users_list`.`user_id` = `offers_list`.`offer_carrier_id`" +
      "JOIN `users_role` ON `users_role`.`role_id` = `users_list`.`user_role_id`" +
      "JOIN `users_status` ON `users_status`.`status_id` = `users_list`.`user_status_id`" +
      "WHERE `offers_list`.`offer_order_id`=?";
  private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public List<Offer> findAll()
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_OFFERS)) {
      List<Offer> offers = new ArrayList<>();
      while (resultSet.next()) {
        Offer.Builder offerBuilder = new Offer.Builder();

        offerBuilder.setId(resultSet.getLong(OFFER_ID))
                    .setPrice(resultSet.getDouble(OFFER_PRICE))
                    .setOfferDate(LocalDateTime.parse(resultSet.getString(OFFER_DATE), dtf))
                    .setOfferStatus(Offer.Status.valueOf(resultSet.getString(OFFER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
               .setEmail(resultSet.getString(USER_EMAIL))
               .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
               .setLastName(resultSet.getString(USER_LAST_NAME))
               .setFirstName(resultSet.getString(USER_FIRST_NAME))
               .setPhone(resultSet.getString(USER_PHONE))
               .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
               .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        offerBuilder.setUser(userBuilder.build());

        offers.add(offerBuilder.build());
      }
      return offers;
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage(), e);
    }
  }

  @Override
  public Optional<Offer> findById(Long id)
      throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_OFFER_BY_ID)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      Offer offer = null;
      while (resultSet.next()) {
        Offer.Builder offerBuilder = new Offer.Builder();

        offerBuilder.setId(resultSet.getLong(OFFER_ID))
                    .setPrice(resultSet.getDouble(OFFER_PRICE))
                    .setOfferDate(LocalDateTime.parse(resultSet.getString(OFFER_DATE), dtf))
                    .setOfferStatus(Offer.Status.valueOf(resultSet.getString(OFFER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        offerBuilder.setUser(userBuilder.build());

        offer = offerBuilder.build();
      }
      return Optional.ofNullable(offer);
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage(), e);
    }
  }

  @Override
  public List<Offer> findAllByCarrierId(Long carrierId) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_OFFERS_BY_CARRIER)) {
      statement.setLong(1, carrierId);
      ResultSet resultSet = statement.executeQuery();
      List<Offer> offers = new ArrayList<>();
      while (resultSet.next()) {
        Offer.Builder offerBuilder = new Offer.Builder();

        offerBuilder.setId(resultSet.getLong(OFFER_ID))
                    .setPrice(resultSet.getDouble(OFFER_PRICE))
                    .setOfferDate(LocalDateTime.parse(resultSet.getString(OFFER_DATE), dtf))
                    .setOfferStatus(Offer.Status.valueOf(resultSet.getString(OFFER_STATUS).toUpperCase()));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(USER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        offerBuilder.setUser(userBuilder.build());

        offers.add(offerBuilder.build());
      }
      return offers;
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage(), e);
    }
  }

  @Override
  public List<Offer> findAllByOrderId(Long orderId)
      throws DaoException {
    List<Offer> offers = new ArrayList<>();

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_OFFERS_BY_ORDER)) {
      statement.setLong(1, orderId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Offer.Builder offerBuilder = new Offer.Builder();

        offerBuilder.setId(resultSet.getLong(OFFER_ID))
                    .setPrice(resultSet.getDouble(OFFER_PRICE))
                    .setOfferDate(LocalDateTime.parse(resultSet.getString(OFFER_DATE), dtf));

        User.Builder userBuilder = new User.Builder();

        userBuilder.setId(resultSet.getLong(OFFER_CARRIER_ID))
                   .setEmail(resultSet.getString(USER_EMAIL))
                   .setRegistrationDate(LocalDateTime.parse(resultSet.getString(USER_REGISTRATION_DATE), dtf))
                   .setLastName(resultSet.getString(USER_LAST_NAME))
                   .setFirstName(resultSet.getString(USER_FIRST_NAME))
                   .setPhone(resultSet.getString(USER_PHONE))
                   .setUserRole(User.Role.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()))
                   .setUserStatus(User.Status.valueOf(resultSet.getString(USER_STATUS_NAME).toUpperCase()));
        offerBuilder.setUser(userBuilder.build());

        offers.add(offerBuilder.build());
      }
    } catch (SQLException e) {
      throw new DaoException("Database error. " + e.getMessage(), e);
    }

    return offers;
  }
}
