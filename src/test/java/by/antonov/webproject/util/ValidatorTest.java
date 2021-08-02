package by.antonov.webproject.util;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidatorTest {

  @DataProvider(name = "dataEmailTest")
  public Object[][] dataEmailTest() {
    return new Object[][]{
        {"test@gmail.com", true},
        {"antonov.ma90@gmail.com", true},
        {"test-carrier@gmail.com", true},
        {"test-carrier@gmail", false},
        {"test", false},
        {"1@.", false},
        {null, false}
    };
  }

  @DataProvider(name = "dataPasswordTest")
  public Object[][] dataPasswordTest() {
    return new Object[][]{
        {"qwerty1", true},
        {"123456", true},
        {"PassW0r!", true},
        {"PassW0r !", true},
        {"pass", false},
        {"PassW0r~", false},
        {null, false}
    };
  }

  @DataProvider(name = "dataRouteTest")
  public Object[][] dataRouteTest() {
    return new Object[][]{
        {"Vitebsk - Minsk", true},
        {"Москва - Санкт-Петербург", true},
        {"Москва, пр. Мира д. 375-10 - Санкт-Петербург", true},
        {"1119 - 131", false},
        {"text", false},
        {null, false}
    };
  }

  @DataProvider(name = "dataNameTest")
  public Object[][] dataNameTest() {
    return new Object[][]{
        {"Maksim", true},
        {"Антонов", true},
        {"O'Brien", true},
        {"Dawson jr.", true},
        {"1119 - 131", false},
        {"12324", false},
        {null, false}
    };
  }

  @DataProvider(name = "dataPhoneTest")
  public Object[][] dataPhoneTest() {
    return new Object[][]{
        {"+7 123 123-45-67", true},
        {"+7 (499) 1234567", true},
        {"74991234567", true},
        {"1119", false},
        {"text", false},
        {null, false}
    };
  }

  @Test(dataProvider = "dataEmailTest")
  public void checkEmailTest(String email, boolean expected) {
    boolean actual = Validator.checkEmail(email);

    assertEquals(actual, expected);
  }

  @Test(dataProvider = "dataPasswordTest")
  public void checkPasswordTest(String password, boolean expected) {
    boolean actual = Validator.checkPassword(password);

    assertEquals(actual, expected);
  }

  @Test(dataProvider = "dataRouteTest")
  public void checkRouteTest(String route, boolean expected) {
    boolean actual = Validator.checkRouteText(route);

    assertEquals(actual, expected);
  }

  @Test(dataProvider = "dataPhoneTest")
  public void checkPhoneTest(String phone, boolean expected) {
    boolean actual = Validator.checkPhone(phone);

    assertEquals(actual, expected);
  }

  @Test(dataProvider = "dataNameTest")
  public void checkNameTest(String name, boolean expected) {
    boolean actual = Validator.checkName(name);

    assertEquals(actual, expected);
  }
}
