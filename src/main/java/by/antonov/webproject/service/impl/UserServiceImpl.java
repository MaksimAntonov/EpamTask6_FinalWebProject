package by.antonov.webproject.service.impl;

import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.dao.UserDao;
import by.antonov.webproject.dao.impl.UserDaoImpl;
import by.antonov.webproject.entity.LogInData;
import by.antonov.webproject.exception.DaoException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.UserService;
import by.antonov.webproject.util.PasswordHash;
import by.antonov.webproject.util.Validator;
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
}
