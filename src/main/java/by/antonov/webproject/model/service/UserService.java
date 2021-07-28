package by.antonov.webproject.model.service;

import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for Service class for work with Users
 */
public interface UserService {

  /**
   * Get all users
   * @return List of Users
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<User> getUsersList()
      throws ServiceException;

  /**
   * Get all users
   * @param limit maximum records
   * @return List of Users
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<User> getUsersList(int limit)
      throws ServiceException;

  /**
   * Get all users
   * @param status Status of Users
   * @return List of Users
   * @throws ServiceException in case of error(s) from Service implementation
   */
  List<User> getUsersListByStatus(User.Status status)
      throws ServiceException;

  /**
   * Check User login data
   * @param email E-Mail address
   * @param password User password
   * @return TRUE if login data is correct, FALSE if it incorrect
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean checkLogin(String email, String password)
      throws ServiceException;

  /**
   * Create a new user
   * @param email User E-Mail address
   * @param password User password
   * @param passwordConfirm User password
   * @param firstName User first name
   * @param lastName User last name
   * @param phone User phone number
   * @param group User group name
   * @return Map with data from user and result of validation and insert data into database
   * @throws ServiceException in case of error(s) from Service implementation
   */
  Map<ResponseKey, String> registerUser(String email, String password, String passwordConfirm, String firstName,
                                        String lastName, String phone, String group)
      throws ServiceException;

  /**
   * Get User by E-Mail
   * @param email User E-Mail address
   * @return Optional<User> if user with this E-Mail exist.
   * @throws ServiceException in case of error(s) from Service implementation
   */
  Optional<User> getUserByEmail(String email)
      throws ServiceException;

  /**
   * Change user name
   * @param userId ID of User
   * @param firstName new first name
   * @param lastName new last name
   * @return TRUE if data changed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean changeUserName(long userId, String firstName, String lastName)
      throws ServiceException;

  /**
   * Change user phone number
   * @param userId ID of User
   * @param phone new phone number
   * @return TRUE if data changed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean changeUserPhone(long userId, String phone)
      throws ServiceException;

  /**
   * Change user password
   * @param userId ID of User
   * @param password new password
   * @param passwordConfirm password reconfirm
   * @return TRUE if data changed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean changeUserPassword(long userId, String password, String passwordConfirm)
      throws ServiceException;

  /**
   * Block user
   * @param userId ID of User
   * @return TRUE if user was blocked
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean blockUser(long userId)
      throws ServiceException;

  /**
   * Unblock user
   * @param userId ID of User
   * @return TRUE if user was unblocked
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean unblockUser(long userId)
      throws ServiceException;

  /**
   * Check user status
   * @param userId ID of User
   * @param expectedStatus Expected status
   * @return TRUE if status was changed
   * @throws ServiceException in case of error(s) from Service implementation
   */
  boolean checkUserStatus(long userId, User.Status expectedStatus)
      throws ServiceException;
}
