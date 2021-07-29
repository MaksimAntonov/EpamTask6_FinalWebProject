package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for class with SQL requests to `users_list`
 */
public interface UserDao extends BaseDao<Long, User> {

  /**
   * Find all records from database, SORTED BU REGISTRATION DATE
   * @param limit maximum records, which should be in resultSet
   * @return List of result objects of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<User> findAll(int limit)
      throws DaoException;

  /**
   * Find all records from database
   * @param offset offset for result
   * @param limit maximum records, which should be in resultSet
   * @return List of result objects of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<User> findAll(int offset, int limit)
      throws DaoException;

  /**
   * Find password hash by E-Mail
   * @param email E-Mail address of user
   * @return Optional with passwordHash or null if resultSet is empty
   * @throws DaoException DaoException in case of any SQL errors
   */
  Optional<String> findPasswordHashByEmail(String email)
      throws DaoException;

  /**
   * Find user by E-Mail
   * @param email E-Mail address of user
   * @return Optional<User> with passwordHash or null if resultSet is empty
   * @throws DaoException DaoException in case of any SQL errors
   */
  Optional<User> findUserByEmail(String email)
      throws DaoException;

  /**
   * Find User.Status by E-Mail
   * @param userId ID of user
   * @return Optional<Status> with User.Status or null if resultSet is empty
   * @throws DaoException DaoException in case of any SQL errors
   */
  Optional<User.Status> findStatusById(long userId)
      throws DaoException;

  /**
   * Check if E-Mail exists
   * @param email E-Mail address
   * @return TRUE if exist, FALSE if not
   * @throws DaoException DaoException in case of any SQL errors
   */
  boolean existRowsByEmail(String email)
      throws DaoException;

  /**
   * Create a new user
   * @param email E-Mail address of User
   * @param passwordHash  Password hash
   * @param passwordSalt  Password salt
   * @param firstName First name of User
   * @param lastName Last name of User
   * @param phone Phone number of User
   * @param userRoleId ID of User.Role
   * @param userStatusId ID of User.Status
   * @return TRUE if record(s) was inserted, FALSE if record(s) wasn't inserted
   * @throws DaoException DaoException in case of any SQL errors
   */
  boolean insertUser(String email, String passwordHash, String passwordSalt, String firstName, String lastName,
                     String phone, long userRoleId, long userStatusId)
      throws DaoException;

  /**
   * Update user first and last names
   * @param userId ID of User
   * @param firstName new first name
   * @param lastName new last name
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean updateUserName(long userId, String firstName, String lastName)
      throws DaoException;

  /**
   * Update user phone number
   * @param userId ID of User
   * @param phone new phone number
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean updateUserPhone(long userId, String phone)
      throws DaoException;

  /**
   * Update user password
   * @param userId ID of User
   * @param passwordHash new password hash
   * @param passwordSalt new password salt
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean updateUserPassword(long userId, String passwordHash, String passwordSalt)
      throws DaoException;

  /**
   * Update user status
   * @param userId ID of User
   * @param statusId new Status ID
   * @return TRUE if record was updated, FALSE if record can't be updated
   * @throws DaoException in case of any SQL errors
   */
  boolean updateUserStatus(long userId, long statusId)
      throws DaoException;

  /**
   * Return count of rows
   * @return count of rows
   * @throws DaoException in case of any SQL errors
   */
  int countOfUsers()
      throws DaoException;
}
