package by.antonov.webproject.controller;

public enum RequestFieldKey implements EnumBase {
  KEY_STYLE_SUCCESS("success"),
  KEY_STYLE_ERROR("error"),
  KEY_STYLE_INSERT_ERROR("insert_error"),
  KEY_COMMAND("command"),
  KEY_LOCALE("locale"),
  KEY_REDIRECT_URL("redirect_url"),
  KEY_USER_EMAIL("user_email"),
  KEY_USER_PASSWORD("user_password"),
  KEY_USER_PASSWORD_CONFIRM("user_password_confirm"),
  KEY_USER_FIRST_NAME("user_firstname"),
  KEY_USER_LAST_NAME("user_lastname"),
  KEY_USER_PHONE("user_phone"),
  KEY_USER_ROLE("user_group");

  private final String key;

  RequestFieldKey(String key) {
    this.key = key;
  }

  @Override
  public String getValue() {
    return this.key;
  }
}
