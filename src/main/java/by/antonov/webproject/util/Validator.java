package by.antonov.webproject.util;

import static by.antonov.webproject.util.ValidationPattern.VALIDATION_EMAIL_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_NAME_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_PASSWORD_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_PHONE_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_PRICE_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_ROUTE_PATTERN;
import static by.antonov.webproject.util.ValidationPattern.VALIDATION_TEXT_PATTERN;

public class Validator {

  private Validator() {
  }

  public static boolean checkEmail(String email) {
    return (email != null && email.matches(VALIDATION_EMAIL_PATTERN.getValue()));
  }

  public static boolean checkPassword(String password) {
    return (password != null && password.matches(VALIDATION_PASSWORD_PATTERN.getValue()));
  }

  public static boolean checkText(String text) {
    return (text != null && text.matches(VALIDATION_TEXT_PATTERN.getValue()));
  }

  public static boolean checkRouteText(String text) {
    return (text != null && text.matches(VALIDATION_ROUTE_PATTERN.getValue()));
  }

  public static boolean checkName(String name) {
    return (name != null && name.matches(VALIDATION_NAME_PATTERN.getValue()));
  }

  public static boolean checkPhone(String phone) {
    return (phone != null && phone.matches(VALIDATION_PHONE_PATTERN.getValue()));
  }

  public static boolean checkPrice(String price) {
    return (price != null && price.matches(VALIDATION_PRICE_PATTERN.getValue()));
  }
}
