package by.antonov.webproject.controller.command.impl.admin;

import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenUsersListCommand implements Command {

  private final User.Role allowedRole = Role.ADMINISTRATOR;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_403_PAGE);
    }

    try {
      UserService userService = ServiceDefinition.getInstance().getUserService();
      List<User> users = userService.getUsersList();
      request.setAttribute(ResponseKey.RESP_USER_RESULT_LIST.name(), users);
      return new Router(RouterType.FORWARD, RouterPath.USERS_LIST);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    }
  }
}
