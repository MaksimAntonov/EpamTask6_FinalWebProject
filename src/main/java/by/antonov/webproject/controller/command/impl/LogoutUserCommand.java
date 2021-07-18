package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.ProjectException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    HttpSession session = request.getSession();
    session.removeAttribute(SessionKey.USER_OBJ.name());
    return new Router(RouterType.REDIRECT, RouterPath.OPEN_LOGIN_REGISTRATION_PAGE);
  }
}
