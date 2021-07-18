package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.ProjectException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToProfileCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
  }
}
