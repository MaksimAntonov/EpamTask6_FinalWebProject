package by.antonov.webproject.service;

import by.antonov.webproject.exception.ServiceException;

public interface UserService {
  boolean checkLogin(String email, String password)
      throws ServiceException;
}
