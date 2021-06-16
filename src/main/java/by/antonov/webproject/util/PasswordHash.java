package by.antonov.webproject.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
  private PasswordHash() {}

  public static String generateSalt() {
    return BCrypt.gensalt();
  }

  public static String encryptPassword(String userPassword, String salt) {
    return BCrypt.hashpw(userPassword, salt);
  }

  public static boolean check(String userPassword, String hashedPassword) {
    return BCrypt.checkpw(userPassword, hashedPassword);
  }
}
