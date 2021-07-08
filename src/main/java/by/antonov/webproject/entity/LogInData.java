package by.antonov.webproject.entity;

public class LogInData {
  private String email;
  private String passwordHash;
  private String passwordSalt;

  public LogInData(String email, String passwordHash, String passwordSalt) {
    this.email = email;
    this.passwordHash = passwordHash;
    this.passwordSalt = passwordSalt;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getPasswordSalt() {
    return passwordSalt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LogInData)) {
      return false;
    }

    LogInData logInData = (LogInData) o;

    if (email != null ? !email.equals(logInData.email) : logInData.email != null) {
      return false;
    }
    if (passwordHash != null ? !passwordHash.equals(logInData.passwordHash) : logInData.passwordHash != null) {
      return false;
    }
    return passwordSalt != null ? passwordSalt.equals(logInData.passwordSalt) : logInData.passwordSalt == null;
  }

  @Override
  public int hashCode() {
    int result = email != null ? email.hashCode() : 0;
    result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
    result = 31 * result + (passwordSalt != null ? passwordSalt.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LogInData{");
    sb.append("email='").append(email).append('\'');
    sb.append(", passwordHash='").append(passwordHash).append('\'');
    sb.append(", passwordSalt='").append(passwordSalt).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
