package by.antonov.webproject.dao;

import by.antonov.webproject.entity.Offer;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OfferDao extends BaseDao<Long, Offer>{
  List<Offer> findAllByCarrierId(Long carrierId)
      throws DaoException;
  List<Offer> findAllByOrderId(Long orderId)
      throws DaoException;
}
