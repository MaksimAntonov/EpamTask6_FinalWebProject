package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Command interface for Controller
 */
public interface Command {

  /**
   * Method for all commands, which will run command code.
   *
   * @param request HttpServletRequest
   * @return Router for change current page
   * @throws CommandException in case of any exceptions in method
   */
  Router execute(HttpServletRequest request)
      throws CommandException;

  /**
   * Check user group for access to command
   *
   * @param userRole current user role
   * @param role     allowed role
   * @return TRUE if access confirmed, FALSE if access denied
   */
  default boolean checkUserGroup(User.Role userRole, User.Role role) {
    boolean result = false;
    if (userRole != null) {
      if (userRole == role) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Check user group for access to command
   *
   * @param userRole current user role
   * @param roles    array of allowed roles
   * @return TRUE if access confirmed, FALSE if access denied
   */
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
