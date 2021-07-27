package by.antonov.webproject.model.dao;

import by.antonov.webproject.entity.EntityBase;
import by.antonov.webproject.exception.DaoException;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base interface classes with SQL requests
 */
public interface BaseDao<K, T extends EntityBase> {

  Logger logger = LogManager.getLogger(BaseDao.class);

  /**
   * Find all records from database
   * @return List of result objects of SQL request
   * @throws DaoException in case of any SQL errors
   */
  List<T> findAll()
      throws DaoException;

  /**
   * Find record by record id
   * @param id record ID
   * @return Optional with result Object of SQL request or null if resultSet is empty
   * @throws DaoException in case of any SQL errors
   */
  Optional<T> findById(K id)
      throws DaoException;
}
