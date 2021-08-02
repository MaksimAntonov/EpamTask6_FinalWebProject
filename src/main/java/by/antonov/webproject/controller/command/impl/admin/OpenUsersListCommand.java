package by.antonov.webproject.controller.command.impl.admin;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_CURRENT_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_MAX_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_USER_RESULT_LIST;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class OpenUsersListCommand implements Command {

  private final User.Role allowedRole = Role.ADMINISTRATOR;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_403_PAGE);
    }

    try {
      UserService userService = ServiceDefinition.getInstance().getUserService();
      int limit = 10;

      String pageString = Optional.ofNullable(request.getParameter(KEY_PAGE.getValue()))
                                  .orElse("1");
      int currentPage = Integer.parseInt(pageString);

      double countOfOrders = userService.countOfUsers();
      int maxPage = (int) Math.ceil(countOfOrders / limit);

      if (maxPage < currentPage) {
        currentPage = maxPage;
      }

      List<User> users = userService.getUsersList(currentPage, limit);

      request.setAttribute(RESP_USER_RESULT_LIST.name(), users);
      request.setAttribute(RESP_CURRENT_PAGE.name(), currentPage);
      request.setAttribute(RESP_MAX_PAGE.name(), maxPage);
      return new Router(RouterType.FORWARD, RouterPath.USERS_LIST);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    }
  }
}
