package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.command.Router;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request, HttpServletResponse response)
      throws ProjectException {
    UserService userService = ServiceDefinition.getInstance().getUserService();
    Router router = null;

    return router;
  }
}
