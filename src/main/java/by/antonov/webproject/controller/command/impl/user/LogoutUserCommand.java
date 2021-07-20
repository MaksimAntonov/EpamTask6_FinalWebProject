package by.antonov.webproject.controller.command.impl.user;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
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
    session.removeAttribute(SessionKey.USER_OBJ.name());
    session.setAttribute(SessionKey.USER_ROLE.name(), Role.GUEST);
    return new Router(RouterType.REDIRECT, RouterPath.OPEN_LOGIN_PAGE);
  }
}
