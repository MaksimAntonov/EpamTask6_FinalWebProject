package by.antonov.webproject.localization;

import by.antonov.webproject.controller.EnumBase;

public enum LocalizeKey implements EnumBase {
  // Error texts
  TEXT_ERROR_403_TITLE("error_403.page_title"),
  TEXT_ERROR_403_MESSAGE("error_403.page_message"),
  TEXT_ERROR_404_TITLE("error_404.page_title"),
  TEXT_ERROR_404_MESSAGE("error_404.page_message"),
  TEXT_ERROR_RUNTIME_TITLE("error_runtime.page_title"),
  TEXT_ERROR_RUNTIME_STATUS_CODE("error_runtime.status_code"),
  TEXT_ERROR_RUNTIME_REQUESTED_URI("error_runtime.requested_uri"),
  TEXT_ERROR_RUNTIME_SERVLET_NAME("error_runtime.servlet_name"),
  TEXT_ERROR_RUNTIME_EXCEPTION_TYPE("error_runtime.exception_type"),
  TEXT_ERROR_RUNTIME_EXCEPTION_MESSAGE("error_runtime.exception_message"),

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
  TEXT_REGISTRATION_SUBMIT("registration.submit"),

  // Profile texts
  TEXT_PROFILE_PAGE_TITLE("profile.page_title"),
  TEXT_PROFILE_UPDATE_SUCCESS_MESSAGE("profile.update_success_message"),
  TEXT_PROFILE_UPDATE_ERROR_MESSAGE("profile.update_error_message"),
  TEXT_PROFILE_CONTACT_PARAMETER_EMAIL("profile.contact_parameter_email"),
  TEXT_PROFILE_CONTACT_PARAMETER_PHONE("profile.contact_parameter_phone"),
  TEXT_PROFILE_EDIT_LABEL("profile.edit_label"),
  TEXT_PROFILE_BUTTON_USER_NAME("profile.button_user_name"),
  TEXT_PROFILE_BUTTON_USER_PHONE("profile.button_user_phone"),
  TEXT_PROFILE_BUTTON_USER_PASSWORD("profile.button_user_password"),
  TEXT_PROFILE_UPDATE_NAME_HEADER("profile.update_name_header"),
  TEXT_PROFILE_UPDATE_PHONE_HEADER("profile.update_phone_header"),
  TEXT_PROFILE_UPDATE_PASSWORD_HEADER("profile.update_password_header"),
  TEXT_PROFILE_BUTTON_UPDATE_SAVE("profile.update_save_button_text"),

  TEXT_HEADER_LOGIN_LINK_TEXT("header.login_link_text"),
  TEXT_HEADER_REGISTRATION_LINK_TEXT("header.registration_link_text"),
  TEXT_HEADER_PROFILE_LINK_TEXT("header.profile_link_text"),
  TEXT_HEADER_LOGOUT_LINK_TEXT("header.logout_link_text"),
  ;
  private final String key;

  LocalizeKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return this.key;
  }
}
