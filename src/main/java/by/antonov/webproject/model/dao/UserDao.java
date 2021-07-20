package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
  Optional<String> findPasswordHashByEmail(String email)
      throws DaoException;
  Optional<User> findUserByEmail(String email)
      throws DaoException;
  boolean existRowsByEmail(String email)
      throws DaoException;
  boolean insertUser(String email, String passwordHash, String passwordSalt, String firstName, String lastName,
                     String phone, long userRoleId, long userStatusId)
      throws DaoException;
  boolean updateUserName(Long userId, String firstName, String lastName)
      throws DaoException;
  boolean updateUserPhone(Long userId, String phone)
      throws DaoException;
  boolean updateUserPassword(Long userId, String passwordHash, String passwordSalt)
      throws DaoException;
}
