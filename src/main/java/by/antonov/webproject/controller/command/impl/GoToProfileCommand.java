package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToProfileCommand implements Command {
  private final User.Role[] allowedRole = new User.Role[] { Role.CARRIER, Role.SHIPPER, Role.ADMINISTRATOR };

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
  }
}
