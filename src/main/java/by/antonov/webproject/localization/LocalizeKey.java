package by.antonov.webproject.localization;

import by.antonov.webproject.controller.EnumBase;

public enum LocalizeKey implements EnumBase {
  // Login texts
  TEXT_LOGIN_PAGE_TITLE("login.page_title"),
  TEXT_LOGIN_HEADER("login.header"),
  TEXT_LOGIN_EMAIL("login.email"),
  TEXT_LOGIN_PASSWORD("login.password"),
  TEXT_LOGIN_ERROR("login.login_error"),
  TEXT_LOGIN_SUBMIT("login.submit"),

  // Registration texts
  TEXT_REGISTRATION_HEADER("registration.header"),
  TEXT_REGISTRATION_EMAIL("registration.email"),
  TEXT_REGISTRATION_PASSWORD("registration.password"),
  TEXT_REGISTRATION_PASSWORD_CONFIRM("registration.password_confirm"),
  TEXT_REGISTRATION_FIRST_NAME("registration.first_name"),
  TEXT_REGISTRATION_LAST_NAME("registration.last_name"),
  TEXT_REGISTRATION_PHONE("registration.phone"),
  TEXT_REGISTRATION_ROLE("registration.role"),
  TEXT_REGISTRATION_CHOOSE_ROLE("registration.choose_role"),
  TEXT_REGISTRATION_SHIPPER("registration.shipper_name"),
  TEXT_REGISTRATION_CARRIER("registration.carrier_name"),
  TEXT_REGISTRATION_SUCCESS_MESSAGE("registration.success_status_message"),
  TEXT_REGISTRATION_ERROR_MESSAGE("registration.error_status_message"),
  TEXT_REGISTRATION_INSERT_ERROR_MESSAGE("registration.insert_error_status_message"),
  TEXT_REGISTRATION_SUBMIT("registration.submit");
  private final String key;

  LocalizeKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return this.key;
  }
}
