package by.antonov.webproject.controller;

public enum RequestFieldKey implements EnumBase {
  KEY_STYLE_SUCCESS("success"),
  KEY_STYLE_ERROR("error"),
  KEY_STYLE_INSERT_ERROR("insert_error"),
  KEY_COMMAND("command"),
  KEY_LOCALE("locale"),
  KEY_REDIRECT_URL("redirect_url"),
  KEY_USER_EMAIL("email"),
  KEY_USER_PASSWORD("password"),
  KEY_USER_PASSWORD_CONFIRM("password_confirm"),
  KEY_USER_FIRST_NAME("firstname"),
  KEY_USER_LAST_NAME("lastname"),
  KEY_USER_PHONE("phone"),
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
