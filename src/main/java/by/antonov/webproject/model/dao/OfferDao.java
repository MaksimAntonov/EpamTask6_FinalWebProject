package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OfferDao extends BaseDao<Long, Offer>{
  List<Offer> findAllByCarrierId(Long carrierId)
      throws DaoException;
  List<Offer> findAllByOrderId(Long orderId)
      throws DaoException;

  int countOffersByOrderId(long orderId)
      throws DaoException;

  boolean acceptOfferById(long offerId)
      throws DaoException;
  boolean denyOffersByOrderId(long orderId)
      throws DaoException;
}
