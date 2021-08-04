package by.antonov.webproject.model.connection;

import static by.antonov.webproject.model.connection.DatabaseProperty.DB_ENCODING;
import static by.antonov.webproject.model.connection.DatabaseProperty.DB_PASSWORD;
import static by.antonov.webproject.model.connection.DatabaseProperty.DB_TIMEZONE;
import static by.antonov.webproject.model.connection.DatabaseProperty.DB_URL;
import static by.antonov.webproject.model.connection.DatabaseProperty.DB_USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
  private static final Properties connectionProperties;

  static {
    connectionProperties = new Properties();
    connectionProperties.put("user", DB_USER);
    connectionProperties.put("password", DB_PASSWORD);
    connectionProperties.put("serverTimezone", DB_TIMEZONE);
    connectionProperties.put("useUnicode", true);
    connectionProperties.put("characterEncoding", DB_ENCODING);
  }

  private ConnectionFactory() {
  }

  static Connection createConnection()
      throws SQLException {
    return DriverManager.getConnection(DB_URL, connectionProperties);
  }
}
