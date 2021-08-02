package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

/**
 * Interface for class with SQL requests to `offers_list`
 */
public interface OfferDao extends BaseDao<Long, Offer> {

  /**
   * Find all Offers by Order ID
   *
   * @param orderId ID from Order record
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Offer> findAllByOrderId(long orderId)
      throws DaoException;

  /**
   * Find all Offers from carrier by Order ID
   *
   * @param orderId   ID of Order record
   * @param carrierId ID of user
   * @return List<Offer> result of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<Offer> findAllByOrderIdAndCarrierId(long orderId, long carrierId)
      throws DaoException;

  /**
   * Get count of records for Order ID
   *
   * @param orderId ID of Order record
   * @return count of offers for order ID
   * @throws DaoException in case of any SQL errors
   */
  int countOffersByOrderId(long orderId)
      throws DaoException;

  /**
   * Accept offer (change status to Accepted)
   *
   * @param offerId ID of offer
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean acceptOfferById(long offerId)
      throws DaoException;

  /**
   * Decline all offers by Order ID (change status to Denied)
   *
   * @param orderId ID of order
   * @return TRUE if record(s) was updated, FALSE if record(s) can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean declineOffersByOrderId(long orderId)
      throws DaoException;

  /**
   * Decline all offers by Order ID (change status to Denied)
   *
   * @param orderId        ID of order
   * @param excludeOfferId ID of offer, which shouldn't declined
   * @return TRUE if record(s) was updated, FALSE if record(s) can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean declineOffersByOrderId(long orderId, long excludeOfferId)
      throws DaoException;

  /**
   * Decline all offers by Order ID (change status to Denied)
   *
   * @param offerId ID of offer
   * @return TRUE if record(s) was updated, FALSE if record(s) can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean declineOfferById(long offerId)
      throws DaoException;

  /**
   * Insert new record
   *
   * @param price     offer price
   * @param carrierId ID of user
   * @param orderId   ID of order
   * @return TRUE if record(s) was inserted, FALSE if record(s) wasn't inserted
   * @throws DaoException in case of any SQL errors
   */
  boolean insertOffer(int price, long carrierId, long orderId)
      throws DaoException;
}
