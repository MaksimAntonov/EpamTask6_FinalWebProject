package by.antonov.webproject.service.impl;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.UserRole;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.UserService;
import by.antonov.webproject.util.PasswordHash;
import by.antonov.webproject.util.Validator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger();
  private final UserDao userDao = DaoDefinition.getInstance().getUserDao();

  @Override
  public boolean checkLogin(String email, String password)
      throws ServiceException {
    boolean result = false;
    if (Validator.checkEmail(email) && Validator.checkPassword(password)) {
      try {
        Optional<Map<ResponceKey, String>> logInDataOptional = userDao.findLoginDataByEmail(email);
        if (logInDataOptional.isPresent()) {
          Map<ResponceKey, String> logInData = logInDataOptional.get();
          result = PasswordHash.check(password, logInData.get(ResponceKey.RESP_LOGIN_PASSWORD));
        }
      } catch (DaoException daoException) {
        logger.error("checkLogin > Can not read data from database: {}", daoException.getMessage());
        throw new ServiceException("Can not read data from database", daoException);
      }
    }
    return result;
  }

  @Override
  public Map<ResponceKey, String> registerUser(String email,
                                               String password,
                                               String passwordConfirm,
                                               String firstName,
                                               String lastName,
                                               String phone,
                                               String group)
      throws ServiceException {
    Map<ResponceKey, String> result = new EnumMap<>(ResponceKey.class);
    int resultSize = 7;
    result.put(ResponceKey.RESP_REGISTRATION_EMAIL, email);
    result.put(ResponceKey.RESP_REGISTRATION_PASSWORD, password);
    result.put(ResponceKey.RESP_REGISTRATION_PASSWORD_CONFIRM, passwordConfirm);
    result.put(ResponceKey.RESP_REGISTRATION_FIRST_NAME, firstName);
    result.put(ResponceKey.RESP_REGISTRATION_LAST_NAME, lastName);
    result.put(ResponceKey.RESP_REGISTRATION_PHONE, phone);
    result.put(ResponceKey.RESP_REGISTRATION_GROUP, group);
    result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "success");

    if (!Validator.checkEmail(email)) {
      result.remove(ResponceKey.RESP_REGISTRATION_EMAIL);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }
    if (!Validator.checkPassword(password) || !Validator.checkPassword(passwordConfirm) || !password.equals(passwordConfirm)) {
      result.remove(ResponceKey.RESP_REGISTRATION_PASSWORD);
      result.remove(ResponceKey.RESP_REGISTRATION_PASSWORD_CONFIRM);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }
    if (!Validator.checkName(firstName)) {
      result.remove(ResponceKey.RESP_REGISTRATION_FIRST_NAME);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }
    if (!Validator.checkName(lastName)) {
      result.remove(ResponceKey.RESP_REGISTRATION_LAST_NAME);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }
    if (!Validator.checkPhone(phone)) {
      result.remove(ResponceKey.RESP_REGISTRATION_PHONE);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }
    if (group != null && !group.toUpperCase().equals(UserRole.SHIPPER.name()) && !group.toUpperCase().equals(UserRole.CARRIER.name())) {
      result.remove(ResponceKey.RESP_REGISTRATION_GROUP);
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
    }

    if (result.get(ResponceKey.RESP_REGISTRATION_RESULT_STATUS).equals("success")) {
      // dao request
      try {
        String passwordSalt = PasswordHash.generateSalt();
        String passwordHash = PasswordHash.encryptPassword(password, passwordSalt);

        Optional<Long> roleIdOpt = userDao.findRoleIdByName(group.toUpperCase());
        if (roleIdOpt.isPresent()) {
          Long roleId = roleIdOpt.get();
          if (!userDao.insertUser(email, passwordHash, passwordSalt, firstName, lastName,phone, roleId)) {
            result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "insert_error");
          }
        }
      } catch (DaoException e) {
        throw new ServiceException("Can not read data from database", e);
      }
    }

    return result;
  }

  @Override
  public Optional<User> getUserByEmail(String email)
      throws ServiceException {
    User user = null;
    try {
      Optional<User> userOpt = userDao.findUserByEmail(email);
      if (userOpt.isPresent()) {
        user = userOpt.get();
      }
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database", daoException);
    }
    return Optional.ofNullable(user);
  }
}
