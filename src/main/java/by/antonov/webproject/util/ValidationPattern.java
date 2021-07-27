package by.antonov.webproject.util;

import by.antonov.webproject.controller.EnumBase;

public enum ValidationPattern implements EnumBase {
  VALIDATION_EMAIL_PATTERN("(?=^.{3,32}$)^([A-Za-z0-9_.-])+@([A-Za-z0-9_-]){2,}\\.([a-z]){2,}$"),
  VALIDATION_PASSWORD_PATTERN("(?=^.{6,20}$)^([A-zА-яЁё0-9_!@ -]){6,20}$"),
  VALIDATION_ROUTE_PATTERN("(?=^.{6,200}$)^(?=.*[A-zА-яЁё])([^<>])[A-zА-яЁё0-9,. -]+ - [A-zА-яЁё0-9,. -]+$"),
  VALIDATION_TEXT_PATTERN("^([^<>])+$"),
  VALIDATION_NAME_PATTERN("(?=^.{1,64}$)^([A-zА-яЁё`\"'. -])+$"),
  VALIDATION_PHONE_PATTERN("(?=^.{7,25}$)^([+]?[0-9]+[( ]*[0-9]+[) ]*[0-9 -]+)$"),
  VALIDATION_PRICE_PATTERN("(?=^.{2,10}$)^([0-9]+)$");
  private final String pattern;

  ValidationPattern(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String getValue() {
    return this.pattern;
  }
}
