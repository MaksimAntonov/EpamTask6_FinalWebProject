package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public interface Command {
  Router execute(HttpServletRequest request)
      throws CommandException;

  default boolean checkUserGroup(User.Role userRole, User.Role role) {
    boolean result = false;
    if (userRole != null) {
      if (userRole == role) {
        result = true;
      }
    }
    return result;
  }

  default boolean checkUserGroup(User.Role userRole, User.Role[] roles) {
    boolean result = false;
    if (userRole != null) {
      for (User.Role role : roles) {
        if (userRole == role) {
          result = true;
          break;
        }
      }
    }
    return result;
  }
}
