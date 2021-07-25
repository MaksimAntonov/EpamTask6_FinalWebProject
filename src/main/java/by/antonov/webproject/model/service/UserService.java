package by.antonov.webproject.model.service;

import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ServiceException;
import java.util.Map;
import java.util.Optional;

public interface UserService {
  boolean checkLogin(String email, String password)
      throws ServiceException;

  Map<ResponseKey, String> registerUser(String email, String password, String passwordConfirm, String firstName,
                                        String lastName, String phone, String group)
      throws ServiceException;
  Optional<User> getUserByEmail(String email)
      throws ServiceException;
  boolean changeUserName(Long userId, String firstName, String lastName)
      throws ServiceException;
  boolean changeUserPhone(Long userId, String phone)
      throws ServiceException;
  boolean changeUserPassword(Long userId, String password, String passwordConfirm)
      throws ServiceException;
}
