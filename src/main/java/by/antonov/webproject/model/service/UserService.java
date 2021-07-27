package by.antonov.webproject.model.service;

import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
  List<User> getUsersList() throws ServiceException;
  List<User> getUsersListByStatus(User.Status status) throws ServiceException;

  boolean checkLogin(String email, String password)
      throws ServiceException;

  Map<ResponseKey, String> registerUser(String email, String password, String passwordConfirm, String firstName,
                                        String lastName, String phone, String group)
      throws ServiceException;
  Optional<User> getUserByEmail(String email)
      throws ServiceException;
  boolean changeUserName(long userId, String firstName, String lastName)
      throws ServiceException;
  boolean changeUserPhone(long userId, String phone)
      throws ServiceException;
  boolean changeUserPassword(long userId, String password, String passwordConfirm)
      throws ServiceException;
  boolean banUser(long userId) throws ServiceException;
  boolean unbanUser(long userId) throws ServiceException;
  boolean checkUserStatus(long userId, User.Status requiredStatus) throws ServiceException;
}
