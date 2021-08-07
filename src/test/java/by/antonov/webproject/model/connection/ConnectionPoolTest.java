package by.antonov.webproject.model.connection;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ConnectionPoolTest {

  @BeforeClass
  public void init() {
    ConnectionPool.getInstance();
  }

  @Test
  public void releaseConnectionNull() {
    boolean actual = ConnectionPool.getInstance().releaseConnection(null);
    assertFalse(actual);
  }

  @Test
  public void releaseConnection() {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = connectionPool.getConnection();
    boolean actual = connectionPool.releaseConnection(connection);
    assertTrue(actual);
  }

  @DataProvider(name = "wrongConnectionsData")
  public Object[][] wrongConnectionsData() throws SQLException {
    return new Object[][]{
        {ConnectionCreator.createConnection()},
        {new ProxyConnection(ConnectionCreator.createConnection())}
    };
  }

  @Test(dataProvider = "wrongConnectionsData")
  public void releaseWrongConnection(Connection connection) {
    boolean actual = ConnectionPool.getInstance().releaseConnection(connection);
    assertFalse(actual);
  }

  @AfterClass
  public void afterClass() {
    ConnectionPool.getInstance().destroyPool();
  }
}
