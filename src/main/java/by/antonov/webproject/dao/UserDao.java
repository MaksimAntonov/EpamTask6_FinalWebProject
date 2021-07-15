package by.antonov.webproject.dao;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import java.util.Map;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
  Optional<Map<ResponceKey, String>> findLoginDataByEmail(String email)
      throws DaoException;
  Optional<Long> findRoleIdByName(String roleName)
      throws DaoException;
  Optional<User> findUserByEmail(String email)
      throws DaoException;
  boolean insertUser(String email, String passwordHash, String passwordSalt, String firstName, String lastName,
                     String phone, long userRole)
      throws DaoException;
}
