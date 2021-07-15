package by.antonov.webproject.service;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ServiceException;
import java.util.Map;
import java.util.Optional;

public interface UserService {
  boolean checkLogin(String email, String password)
      throws ServiceException;

  Map<ResponceKey, String> registerUser(String email, String password, String passwordConfirm, String firstName,
                                        String lastName, String phone, String group)
      throws ServiceException;
  Optional<User> getUserByEmail(String email)
      throws ServiceException;
}
