package by.antonov.webproject.entity;

import java.time.LocalDateTime;

public class User extends EntityBase{
  private long id;
  private String email;
  private LocalDateTime registrationDate;
  private String firstName;
  private String lastName;
  private String phone;
  private UserRole userRole;
  private UserStatus userStatus;

  public User(long id,
              String email,
              LocalDateTime registrationDate,
              String firstName,
              String lastName,
              String phone, UserRole userRole, UserStatus userStatus) {
    this.id = id;
    this.email = email;
    this.registrationDate = registrationDate;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.userRole = userRole;
    this.userStatus = userStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }

    User user = (User) o;

    if (id != user.id) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    if (registrationDate != null ? !registrationDate.equals(user.registrationDate) : user.registrationDate != null) {
      return false;
    }
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
      return false;
    }
    if (phone != null ? !phone.equals(user.phone) : user.phone != null) {
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
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
    result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", email='").append(email).append('\'');
    sb.append(", registrationDate=").append(registrationDate);
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", userRole=").append(userRole);
    sb.append(", userStatus=").append(userStatus);
    sb.append('}');
    return sb.toString();
  }

  public static class Builder {
    private long id;
    private String email;
    private LocalDateTime registrationDate;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setRegistrationDate(LocalDateTime registrationDate) {
      this.registrationDate = registrationDate;
      return this;
    }

    public Builder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setUserRole(UserRole userRole) {
      this.userRole = userRole;
      return this;
    }

    public Builder setUserStatus(UserStatus userStatus) {
      this.userStatus = userStatus;
      return this;
    }

    public User build() {
      return new User(id, email, registrationDate, firstName, lastName, phone, userRole, userStatus);
    }
  }
}
