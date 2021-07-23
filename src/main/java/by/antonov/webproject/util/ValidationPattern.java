package by.antonov.webproject.util;

import by.antonov.webproject.controller.EnumBase;

public enum ValidationPattern implements EnumBase {
  VALIDATION_EMAIL_PATTERN("^([A-Za-z0-9_.-])+@([A-Za-z0-9_-]){2,}.([a-z]){2,}$"),
  VALIDATION_PASSWORD_PATTERN("^([A-zА-яЁё0-9_!@ -]){6,20}$"),
  VALIDATION_TEXT_PATTERN("^([^<>])+$"),
  VALIDATION_NAME_PATTERN("^([A-zА-яЁё -])+$"),
  VALIDATION_PHONE_PATTERN("^([+]?[0-9]+[( ]*[0-9]+[) ]*[0-9 -]+)$");
  private final String pattern;

  ValidationPattern(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String getValue() {
    return this.pattern;
  }
}
