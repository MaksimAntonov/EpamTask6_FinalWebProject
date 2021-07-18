package by.antonov.webproject.service.impl;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
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
        Optional<String> passwordHashOptional = userDao.findPasswordHashByEmail(email);
        if (passwordHashOptional.isPresent()) {
          result = PasswordHash.check(password, passwordHashOptional.get());
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

    if (Validator.checkEmail(email)) {
      result.put(ResponceKey.RESP_REGISTRATION_EMAIL, email);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkPassword(password) && Validator.checkPassword(passwordConfirm) && password.equals(passwordConfirm)) {
      result.put(ResponceKey.RESP_REGISTRATION_PASSWORD, password);
      result.put(ResponceKey.RESP_REGISTRATION_PASSWORD_CONFIRM, passwordConfirm);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkName(firstName)) {
      result.put(ResponceKey.RESP_REGISTRATION_FIRST_NAME, firstName);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkName(lastName)) {
      result.put(ResponceKey.RESP_REGISTRATION_LAST_NAME, lastName);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkPhone(phone)) {
      result.put(ResponceKey.RESP_REGISTRATION_PHONE, phone);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (group != null && (group.equalsIgnoreCase(User.Role.SHIPPER.name()) || group.equalsIgnoreCase(User.Role.CARRIER.name()))) {
      result.put(ResponceKey.RESP_REGISTRATION_GROUP, group);
    } else {
      result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (!result.containsKey(ResponceKey.RESP_REGISTRATION_RESULT_STATUS)) {
      try {
        String passwordSalt = PasswordHash.generateSalt();
        String passwordHash = PasswordHash.encryptPassword(password, passwordSalt);

        long roleId = User.Role.valueOf(group.toUpperCase()).ordinal();
        boolean inserted = userDao.insertUser(email, passwordHash, passwordSalt, firstName, lastName,phone, roleId);
        result.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS,
                   ((inserted) ? RequestFieldKey.KEY_STYLE_SUCCESS.getValue()
                               : RequestFieldKey.KEY_STYLE_INSERT_ERROR.getValue()));
      } catch (DaoException daoException) {
        throw new ServiceException("Can not read data from database", daoException);
      }
    }

    return result;
  }

  @Override
  public Optional<User> getUserByEmail(String email)
      throws ServiceException {
    try {
      Optional<User> userOptional = userDao.findUserByEmail(email);
      User user = null;
      if (userOptional.isPresent()) {
        user = userOptional.get();
      }
      return Optional.ofNullable(user);
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database", daoException);
    }
  }

  @Override
  public boolean changeUserName(Long userId, String firstName, String lastName)
      throws ServiceException {
    try {
      boolean result = false;
      if (Validator.checkName(firstName) && Validator.checkName(lastName)) {
        result = userDao.updateUserName(userId, firstName, lastName);
      }
      return result;
    } catch (DaoException daoException) {
      throw new ServiceException("Can not update data in database", daoException);
    }
  }

  @Override
  public boolean changeUserPhone(Long userId, String phone)
      throws ServiceException {
    try {
      boolean result = false;
      if (Validator.checkPhone(phone)) {
        result = userDao.updateUserPhone(userId, phone);
      }
      return result;
    } catch (DaoException daoException) {
      throw new ServiceException("Can not update data in database", daoException);
    }
  }

  @Override
  public boolean changeUserPassword(Long userId, String password, String passwordConfirm)
      throws ServiceException {
    try {
      boolean result = false;
      if (Validator.checkPassword(password) && Validator.checkPassword(passwordConfirm) && password.equals(passwordConfirm)) {
        String passwordSalt = PasswordHash.generateSalt();
        String passwordHash = PasswordHash.encryptPassword(password, passwordSalt);

        result = userDao.updateUserPassword(userId, passwordHash, passwordSalt);
      }
      return result;
    } catch (DaoException daoException) {
      throw new ServiceException("Can not update data in database", daoException);
    }
  }
}
