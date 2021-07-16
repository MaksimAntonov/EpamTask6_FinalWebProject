package by.antonov.webproject.dao;

import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
  Optional<String> findPasswordHashByEmail(String email)
      throws DaoException;
  Optional<User> findUserByEmail(String email)
      throws DaoException;
  boolean insertUser(String email, String passwordHash, String passwordSalt, String firstName, String lastName,
                     String phone, long userRole)
      throws DaoException;
}
