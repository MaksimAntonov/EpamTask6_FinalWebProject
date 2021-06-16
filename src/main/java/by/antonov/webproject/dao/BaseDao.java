package by.antonov.webproject.dao;

import by.antonov.webproject.entity.EntityBase;
import by.antonov.webproject.exception.DaoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface BaseDao <K,T extends EntityBase> {
  Logger logger = LogManager.getLogger(BaseDao.class);

  List<T> findAll()
      throws DaoException;
  Optional<T> findById(K id)
      throws DaoException;

  default void close(Statement statement) {
    try {
      if (statement != null) {
        statement.close();
      }
    } catch (SQLException e) {
      logger.error("Can not close statement {}", e.getMessage());
    }
  }

  default void close(Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      logger.error("Can not close connection {}", e.getMessage());
    }
  }
}
