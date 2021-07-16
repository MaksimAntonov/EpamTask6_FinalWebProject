package by.antonov.webproject.controller.command.impl;

import static by.antonov.webproject.controller.RequestFieldKey.*;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.LocalizeKey;
import by.antonov.webproject.localization.Localizer;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class LoginUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    HttpSession session = request.getSession();
    String currentLocale = (String) session.getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localizer localizer = Localizer.valueOf(currentLocale.toUpperCase());
    try {
      Router router;
      String email = request.getParameter(KEY_USER_EMAIL.getValue());
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      Optional<User> userOpt;
      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.checkLogin(email, password) && (userOpt = userService.getUserByEmail(email)).isPresent()) {
        session.setAttribute(SessionKey.USER_OBJ.name(), userOpt.get());
        router = new Router(RouterType.FORWARD, RouterPath.LOGIN_REGISTRATION_PAGE);
      } else {
        request.setAttribute(ResponceKey.RESP_LOGIN_RESULT_STATUS.name(), "error");
        request.setAttribute(ResponceKey.RESP_LOGIN_RESULT_MESSAGE.name(), localizer.getText(LocalizeKey.TEXT_LOGIN_ERROR));
        router = new Router(RouterType.FORWARD, RouterPath.LOGIN_REGISTRATION_PAGE);
      }
      return router;
    } catch (ServiceException serviceException) {
      throw new ProjectException("Login command error.", serviceException);
    }
  }
}
