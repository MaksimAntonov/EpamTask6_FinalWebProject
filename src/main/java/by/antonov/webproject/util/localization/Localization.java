package by.antonov.webproject.util.localization;

import java.util.ResourceBundle;

public enum Localization {
  EN("en", "English"),
  RU("ru", "Русский");

  private final String languageCode;
  private final String languageName;
  private final ResourceBundle resourceBundle;

  Localization(String languageCode, String languageName) {
    this.languageCode = languageCode;
    this.languageName = languageName;
    this.resourceBundle = ResourceBundle.getBundle("localization/locale_" + languageCode);
  }

  public ResourceBundle getResourceBundle() {
    return this.resourceBundle;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public String getLanguageName() {
    return languageName;
  }

  public String getText(LocalizationKey key) {
    return this.resourceBundle.getString(key.getValue());
  }

  public String getText(String key) {
    return this.resourceBundle.getString(key);
  }
}
