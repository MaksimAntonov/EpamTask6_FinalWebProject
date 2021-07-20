package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.DaoException;
import java.util.List;

public interface OrderDao extends BaseDao<Long, Order>{
  List<Order> findAllByShipperId(Long id)
      throws DaoException;
}
