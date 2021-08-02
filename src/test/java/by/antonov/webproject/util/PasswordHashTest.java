package by.antonov.webproject.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PasswordHashTest {

  @DataProvider(name = "passwordCheckCorrectData")
  public Object[][] passwordCheckCorrectData() {
    return new Object[][]{
        {"user password", "$2a$10$Y1LX6XJaOd.9l44sXnHdVeCUmrt0v8YPr4bvr..YAa3vwjgK797wS"},
        {"User@passVVord", "$2a$10$aSDxzJz32a2omgu.ltADL.5rsHvKHO5yIqriDwExHYftb4CFP.Y72"}
    };
  }

  @Test(dataProvider = "passwordCheckCorrectData")
  public void passwordCheckCorrectTest(String password, String passwordHash) {
    boolean actual = PasswordHash.check(password, passwordHash);
    assertTrue(actual);
  }

  @DataProvider(name = "passwordCheckWrongData")
  public Object[][] passwordCheckWrongData() {
    return new Object[][]{
        {"user  password", "$2a$10$Y1LX6XJaOd.9l44sXnHdVeCUmrt0v8YPr4bvr..YAa3vwjgK797wS"},
        {"User@passWord", "$2a$10$aSDxzJz32a2omgu.ltADL.5rsHvKHO5yIqriDwExHYftb4CFP.Y72"}
    };
  }

  @Test(dataProvider = "passwordCheckWrongData")
  public void passwordCheckWrongTest(String password, String passwordHash) {
    boolean actual = PasswordHash.check(password, passwordHash);
    assertFalse(actual);
  }
}
