package by.antonov.webproject.entity;

import java.time.LocalDateTime;

public class User extends EntityBase{
  private long id;
  private String login;
  private String email;
  private LocalDateTime registrationDate;
  private UserRole userRole;
  private UserStatus userStatus;

  public User() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public UserStatus getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (id != user.id) {
      return false;
    }
    if (login != null ? !login.equals(user.login) : user.login != null) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    if (registrationDate != null ? !registrationDate.equals(user.registrationDate) : user.registrationDate != null) {
      return false;
    }
    if (userRole != user.userRole) {
      return false;
    }
    return userStatus == user.userStatus;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
    result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
    result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", login='").append(login).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", registrationDate=").append(registrationDate);
    sb.append(", userRole=").append(userRole);
    sb.append(", userStatus=").append(userStatus);
    sb.append('}');
    return sb.toString();
  }
}
