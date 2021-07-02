package by.antonov.webproject.connection;

import static by.antonov.webproject.connection.DatabaseProperties.DB_DRIVER;
import static by.antonov.webproject.connection.DatabaseProperties.DB_ENCODING;
import static by.antonov.webproject.connection.DatabaseProperties.DB_PASSWORD;
import static by.antonov.webproject.connection.DatabaseProperties.DB_TIMEZONE;
import static by.antonov.webproject.connection.DatabaseProperties.DB_URL;
import static by.antonov.webproject.connection.DatabaseProperties.DB_USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ConnectionFactory {
  private ConnectionFactory() {}

  static Connection createConnection()
      throws SQLException {
    Properties connectionProp = new Properties();
    connectionProp.put("user", DB_USER);
    connectionProp.put("password", DB_PASSWORD);
    connectionProp.put("serverTimezone", DB_TIMEZONE);
    connectionProp.put("useUnicode", true);
    connectionProp.put("characterEncoding", DB_ENCODING);

    return DriverManager.getConnection(DB_URL, connectionProp);
  }
}
