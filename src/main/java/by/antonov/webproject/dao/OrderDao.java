package by.antonov.webproject.dao;

import by.antonov.webproject.entity.Order;
import java.util.List;

public interface OrderDao extends BaseDao<Long, Order>{
  List<Order> findAllByShipperId(Long id);
}
