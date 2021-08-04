package by.antonov.webproject.model.connection;

import static by.antonov.webproject.model.connection.DatabaseProperty.DB_DRIVER;
import static by.antonov.webproject.model.connection.DatabaseProperty.DB_POOL_SIZE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connection pool for working with database
 */
public class ConnectionPool {

  private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
  private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
  private static ConnectionPool instance;
  private final BlockingQueue<ProxyConnection> freeConnections;
  private final BlockingQueue<ProxyConnection> busyConnections;

  private ConnectionPool() {
    this.freeConnections = new LinkedBlockingQueue<>(DB_POOL_SIZE);
    this.busyConnections = new LinkedBlockingQueue<>(DB_POOL_SIZE);
    try {
      Class.forName(DB_DRIVER);
      for (int i = 0; i < DB_POOL_SIZE; i++) {
        Connection connection = ConnectionFactory.createConnection();
        freeConnections.add(new ProxyConnection(connection));
      }
    } catch (ClassNotFoundException e) {
      logger.fatal("Can not register database driver {}: {}", DB_DRIVER, e.getMessage());
      throw new ExceptionInInitializerError("Can not register database driver " + DB_DRIVER + ":" + e.getMessage());
    } catch (SQLException e) {
      logger.fatal("Database access error {}", e.getMessage());
      throw new ExceptionInInitializerError("Database access error " + e.getMessage());
    }
  }

  /**
   * Get Connection pool instance
   *
   * @return instance of Connection pool
   */
  public static ConnectionPool getInstance() {
    while (instance == null) {
      if (isInitialized.compareAndSet(false, true)) {
        instance = new ConnectionPool();
      }
    }

    return instance;
  }

  /**
   * Get free connection for database
   *
   * @return Connection
   */
  public Connection getConnection() {
    ProxyConnection connection = null;
    try {
      connection = freeConnections.take();
      busyConnections.put(connection);
    } catch (InterruptedException e) {
      logger.error("Interrupted exception. {}", e.getMessage());
      Thread.currentThread().interrupt();
    }

    return connection;
  }

  /**
   * Release connection after queries to database
   *
   * @param connection connection for release
   * @return TRUE if connection was released
   */
  public boolean releaseConnection(Connection connection) {
    boolean result = false;
    if (connection instanceof ProxyConnection) {
      try {
        if (busyConnections.remove((ProxyConnection) connection)) {
          freeConnections.put((ProxyConnection) connection);
          result = true;
        }
      } catch (InterruptedException e) {
        logger.error("Interrupted exception. {}", e.getMessage());
        Thread.currentThread().interrupt();
      }
    } else {
      logger.error("Wrong connection instance.");
    }
    return result;
  }

  /**
   * Destroy Connection pool and close all opened Connections
   */
  public void destroyPool() {
    for (int i = 0; i < DB_POOL_SIZE; i++) {
      try {
        freeConnections.take().closeConnection();
      } catch (SQLException e) {
        logger.error("SQL Error {}", e.getMessage());
      } catch (InterruptedException e) {
        logger.error("Interrupted exception. {}", e.getMessage());
        Thread.currentThread().interrupt();
      }
    }

    deregisterDrivers();
  }

  private void deregisterDrivers() {
    DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
      try {
        DriverManager.deregisterDriver(driver);
      } catch (SQLException e) {
        logger.error("Deregister driver error {}", e.getMessage());
      }
    });
  }
}
