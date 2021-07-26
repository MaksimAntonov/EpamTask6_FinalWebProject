package by.antonov.webproject.model.service.impl;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.entity.User.Status;
import by.antonov.webproject.model.dao.DaoDefinition;
import by.antonov.webproject.model.dao.UserDao;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.UserService;
import by.antonov.webproject.util.PasswordHash;
import by.antonov.webproject.util.Validator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger();
  private final UserDao userDao = DaoDefinition.getInstance().getUserDao();

  @Override
  public List<User> getUsersList()
      throws ServiceException {
    try {
      return userDao.findAll();
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public List<User> getUsersListByStatus(Status status)
      throws ServiceException {
    // TODO IMPLEMENT
    return null;
  }

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
        throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
      }
    }
    return result;
  }

  @Override
  public Map<ResponseKey, String> registerUser(String email,
                                               String password,
                                               String passwordConfirm,
                                               String firstName,
                                               String lastName,
                                               String phone,
                                               String group)
      throws ServiceException {
    Map<ResponseKey, String> result = new EnumMap<>(ResponseKey.class);

    if (Validator.checkEmail(email)) {
      result.put(ResponseKey.RESP_REGISTRATION_EMAIL, email);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkPassword(password) && Validator.checkPassword(passwordConfirm) && password.equals(passwordConfirm)) {
      result.put(ResponseKey.RESP_REGISTRATION_PASSWORD, password);
      result.put(ResponseKey.RESP_REGISTRATION_PASSWORD_CONFIRM, passwordConfirm);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkName(firstName)) {
      result.put(ResponseKey.RESP_REGISTRATION_FIRST_NAME, firstName);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkName(lastName)) {
      result.put(ResponseKey.RESP_REGISTRATION_LAST_NAME, lastName);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (Validator.checkPhone(phone)) {
      result.put(ResponseKey.RESP_REGISTRATION_PHONE, phone);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (group != null && (group.equalsIgnoreCase(User.Role.SHIPPER.name()) || group.equalsIgnoreCase(User.Role.CARRIER.name()))) {
      result.put(ResponseKey.RESP_REGISTRATION_GROUP, group);
    } else {
      result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
    }

    if (!result.containsKey(ResponseKey.RESP_FORM_RESULT_STATUS)) {
      try {
        String passwordSalt = PasswordHash.generateSalt();
        String passwordHash = PasswordHash.encryptPassword(password, passwordSalt);

        long roleId = User.Role.valueOf(group.toUpperCase()).getDBIndex();
        long statusId = Status.VERIFIED.getDBIndex();
        if (!userDao.existRowsByEmail(email)) {
          if (userDao.insertUser(email, passwordHash, passwordSalt, firstName, lastName, phone, roleId, statusId)) {
            result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_SUCCESS.getValue());
          } else {
            result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_INSERT_ERROR.getValue());
          }
        } else {
          result.put(ResponseKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_INSERT_DUPLICATE_ERROR.getValue());
        }
      } catch (DaoException daoException) {
        throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
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
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean changeUserName(long userId, String firstName, String lastName)
      throws ServiceException {
    try {
      boolean result = false;
      if (Validator.checkName(firstName) && Validator.checkName(lastName)) {
        result = userDao.updateUserName(userId, firstName, lastName);
      }
      return result;
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean changeUserPhone(long userId, String phone)
      throws ServiceException {
    try {
      boolean result = false;
      if (Validator.checkPhone(phone)) {
        result = userDao.updateUserPhone(userId, phone);
      }
      return result;
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean changeUserPassword(long userId, String password, String passwordConfirm)
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
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean banUser(long userId)
      throws ServiceException {
    try {
      return userDao.updateUserStatus(userId, Status.BLOCKED.getDBIndex());
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }

  @Override
  public boolean unbanUser(long userId)
      throws ServiceException {
    try {
      return userDao.updateUserStatus(userId, Status.VERIFIED.getDBIndex());
    } catch (DaoException daoException) {
      throw new ServiceException("Can not read data from database: " + daoException.getMessage(), daoException);
    }
  }
}
