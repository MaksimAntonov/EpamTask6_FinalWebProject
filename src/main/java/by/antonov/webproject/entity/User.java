package by.antonov.webproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User extends EntityBase {

  private final long id;
  private final String email;
  private final LocalDate registrationDate;
  private final Role userRole;
  private final Status userStatus;
  private String firstName;
  private String lastName;
  private String phone;

  protected User(long id,
                 String email,
                 LocalDate registrationDate,
                 String firstName,
                 String lastName,
                 String phone, Role userRole, Status userStatus) {
    this.id = id;
    this.email = email;
    this.registrationDate = registrationDate;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.userRole = userRole;
    this.userStatus = userStatus;
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Role getUserRole() {
    return userRole;
  }

  public Status getUserStatus() {
    return userStatus;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (this.getClass() != o.getClass()) {
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

  public enum Role {
    GUEST,
    ADMINISTRATOR,
    SHIPPER,
    CARRIER;

    public int getDBIndex() {
      return this.ordinal() + 1;
    }
  }

  public enum Status {
    NEW,
    VERIFIED,
    BLOCKED;

    public int getDBIndex() {
      return this.ordinal() + 1;
    }
  }

  public static class Builder {

    private long id;
    private String email;
    private LocalDateTime registrationDate;
    private String firstName;
    private String lastName;
    private String phone;
    private Role userRole;
    private Status userStatus;

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

    public Builder setUserRole(Role userRole) {
      this.userRole = userRole;
      return this;
    }

    public Builder setUserStatus(Status userStatus) {
      this.userStatus = userStatus;
      return this;
    }

    public User build() {
      return new User(id, email, registrationDate.toLocalDate(), firstName, lastName, phone, userRole, userStatus);
    }
  }
}
