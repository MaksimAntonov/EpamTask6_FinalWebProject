package by.antonov.webproject.connection;

import static by.antonov.webproject.connection.DatabaseProperties.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
  private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
  private static ConnectionPool instance;
  private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
  private final BlockingQueue<ProxyConnection> freeConnections;
  private final Queue<ProxyConnection> reservedConnections;

  private ConnectionPool() {
    this.freeConnections = new LinkedBlockingQueue<>(DB_POOL_SIZE);
    this.reservedConnections = new ArrayDeque<>(DB_POOL_SIZE);
    try {
      Class.forName(DB_DRIVER);
      for (int i = 0; i < DB_POOL_SIZE; i++) {
        Connection connection = generateConnectionFromDriverManager();
        freeConnections.add(new ProxyConnection(connection));
      }
    } catch (ClassNotFoundException e) {
      logger.fatal("Can not register database driver {}", DB_DRIVER);
      throw new RuntimeException("Can not register database driver " + DB_DRIVER);
    } catch (SQLException e) {
      logger.fatal("Database access error {}", e.getMessage());
      throw new RuntimeException("Database access error " + e.getMessage());
    }
  }

  public static ConnectionPool getInstance() {
    while (instance == null) {
      if (isInitialized.compareAndSet(false, true)) {
        instance = new ConnectionPool();
      }
    }

    return instance;
  }

  public Connection getConnection() {
    ProxyConnection connection = null;
    try {
      connection = freeConnections.take();
      reservedConnections.add(connection);
    } catch (InterruptedException e) {
      logger.error("Interrupted exception. {}", e.getMessage());
      Thread.currentThread().interrupt();
    }

    return connection;
  }

  public void releaseConnection(Connection connection) {
    if (!(connection instanceof ProxyConnection)) {
      logger.error("Wrong connection instance.");
      throw new RuntimeException("Wrong connection instance.");
    }

    try {
      reservedConnections.remove((ProxyConnection) connection);
      freeConnections.put((ProxyConnection) connection);
    } catch (InterruptedException e) {
      logger.error("Interrupted exception. {}", e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

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

  private Connection generateConnectionFromDriverManager() throws SQLException {
    Properties connectionProp = new Properties();
    connectionProp.put("user", DB_USER);
    connectionProp.put("password", DB_PASSWORD);
    connectionProp.put("serverTimezone", DB_TIMEZONE);
    connectionProp.put("useUnicode", true);
    connectionProp.put("characterEncoding", DB_ENCODING);

    return DriverManager.getConnection(DB_URL, connectionProp);
  }
}
