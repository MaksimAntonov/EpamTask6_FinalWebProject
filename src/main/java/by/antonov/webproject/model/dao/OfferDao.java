package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OfferDao extends BaseDao<Long, Offer> {

  List<Offer> findAllByCarrierId(long carrierId)
      throws DaoException;

  List<Offer> findAllByOrderId(long orderId)
      throws DaoException;

  List<Offer> findAllByOrderIdAndCarrierId(long orderId, long carrierId)
      throws DaoException;

  int countOffersByOrderId(long orderId)
      throws DaoException;

  boolean acceptOfferById(long offerId)
      throws DaoException;

  boolean declineOffersByOrderId(long orderId)
      throws DaoException;

  boolean declineOffersByOrderId(long orderId, long excludeOfferId)
      throws DaoException;

  boolean declineOfferById(long offerId)
      throws DaoException;

  boolean insertOffer(int price, long carrierId, long orderId)
      throws DaoException;
}
