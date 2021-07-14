package by.antonov.webproject.service;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import java.util.Map;

public interface UserService {
  boolean checkLogin(String email, String password)
      throws ServiceException;

  Map<ResponceKey, String> registerUser(String email, String password, String passwordConfirm, String firstName,
                                        String lastName, String phone, String group)
      throws ProjectException;
}
