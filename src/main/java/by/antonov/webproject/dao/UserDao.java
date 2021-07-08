package by.antonov.webproject.dao;

import by.antonov.webproject.entity.LogInData;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
  Optional<LogInData> findLoginDataByEmail(String email)
      throws DaoException;
}
