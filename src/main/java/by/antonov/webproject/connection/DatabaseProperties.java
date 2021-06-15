package by.antonov.webproject.connection;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

class DatabaseProperties {
  static final String DB_DRIVER;
  static final String DB_URL;
  static final int DB_POOL_SIZE;
  static final String DB_USER;
  static final String DB_PASSWORD;
  static final String DB_TIMEZONE;
  static final String DB_ENCODING;

  static {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("configs/database/db");
      DB_DRIVER = resourceBundle.getString("db.driver");
      DB_URL = resourceBundle.getString("db.url");
      DB_POOL_SIZE = Integer.parseInt(resourceBundle.getString("db.pool_size"));
      DB_USER = resourceBundle.getString("db.user");
      DB_PASSWORD = resourceBundle.getString("db.password");
      DB_TIMEZONE = resourceBundle.getString("db.timezone");
      DB_ENCODING = resourceBundle.getString("db.encoding");
    } catch (MissingResourceException e) {
      e.printStackTrace();
      throw new ExceptionInInitializerError("Database properties reading error: " + e.getMessage());
    }
  }

  private DatabaseProperties() {}
}
