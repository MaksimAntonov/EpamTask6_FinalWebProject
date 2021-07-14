package by.antonov.webproject.localization;

import java.util.ResourceBundle;

public enum Localizer {
  EN("en"),
  RU("ru");

  private final ResourceBundle resourceBundle;

  Localizer(String language) {
    this.resourceBundle = ResourceBundle.getBundle("localization/locale_" + language);
  }

  public ResourceBundle getResourceBundle() {
    return this.resourceBundle;
  }

  public String getText(LocalizeKey key) {
    return this.resourceBundle.getString(key.getValue());
  }

  public String getText(String key) {
    return this.resourceBundle.getString(key);
  }
}
