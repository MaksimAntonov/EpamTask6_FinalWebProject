package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.localization.Localizer;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    UserService userService = ServiceDefinition.getInstance().getUserService();
    Router router = null;
    String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    if (currentLocale == null) {
      currentLocale = "en";
    }
    Localizer localizer = Localizer.valueOf(currentLocale.toUpperCase());


    return null;
  }
}
