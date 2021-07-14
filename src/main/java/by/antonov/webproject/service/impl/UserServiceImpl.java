package by.antonov.webproject.service.impl;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.dao.impl.UserDaoImpl;
import by.antonov.webproject.entity.LogInData;
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
        Optional<LogInData> logInDataOptional = userDao.findLoginDataByEmail(email);
        if (logInDataOptional.isPresent()) {
          LogInData logInData = logInDataOptional.get();
          result = PasswordHash.check(password, logInData.getPasswordHash());
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
      throws ProjectException {
    Map<ResponceKey, String> result = new EnumMap<>(ResponceKey.class);
    result.put(ResponceKey.RESP_REGISTRATION_EMAIL, email);
    result.put(ResponceKey.RESP_REGISTRATION_PASSWORD, password);
    result.put(ResponceKey.RESP_REGISTRATION_PASSWORD_CONFIRM, passwordConfirm);
    result.put(ResponceKey.RESP_REGISTRATION_FIRST_NAME, firstName);
    result.put(ResponceKey.RESP_REGISTRATION_LAST_NAME, lastName);
    result.put(ResponceKey.RESP_REGISTRATION_PHONE, phone);
    result.put(ResponceKey.RESP_REGISTRATION_GROUP, group);
    if (!Validator.checkEmail(email)) {
      result.remove(ResponceKey.RESP_REGISTRATION_EMAIL);
    }
    if (!Validator.checkPassword(password) || !Validator.checkPassword(passwordConfirm) || !password.equals(passwordConfirm)) {
      result.remove(ResponceKey.RESP_REGISTRATION_PASSWORD);
      result.remove(ResponceKey.RESP_REGISTRATION_PASSWORD_CONFIRM);
    }
    if (!Validator.checkName(firstName)) {
      result.remove(ResponceKey.RESP_REGISTRATION_FIRST_NAME);
    }
    if (!Validator.checkName(lastName)) {
      result.remove(ResponceKey.RESP_REGISTRATION_LAST_NAME);
    }
    if (!Validator.checkPhone(phone)) {
      result.remove(ResponceKey.RESP_REGISTRATION_PHONE);
    }
    if (!group.toUpperCase().equals(UserRole.SHIPPER.name()) && !group.toUpperCase().equals(UserRole.CARRIER.name())) {
      result.remove(ResponceKey.RESP_REGISTRATION_GROUP);
    }

    

    return result;
  }
}
