package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.SessionKey.USER_OBJ;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    HttpSession session = request.getSession();
    session.removeAttribute(USER_OBJ.name());
    session.setAttribute(USER_ROLE.name(), Role.GUEST);
    return new Router(RouterType.REDIRECT, RouterPath.OPEN_LOGIN_PAGE);
  }
}
