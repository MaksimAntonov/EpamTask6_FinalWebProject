package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.*;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.LocalizationKey;
import by.antonov.webproject.localization.Localization;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class LoginUserCommand implements Command {
  private final User.Role allowedRole = Role.GUEST;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    HttpSession session = request.getSession();
    String currentLocale = (String) session.getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localization localization = Localization.valueOf(currentLocale.toUpperCase());
    try {
      Router router;
      String email = request.getParameter(KEY_USER_EMAIL.getValue());
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      Optional<User> userOpt;
      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.checkLogin(email, password) && (userOpt = userService.getUserByEmail(email)).isPresent()) {
        User user = userOpt.get();
        session.setAttribute(SessionKey.USER_OBJ.name(), user);
        session.setAttribute(SessionKey.USER_ROLE.name(), user.getUserRole());
        router = new Router(RouterType.REDIRECT, RouterPath.OPEN_PROFILE_PAGE);
      } else {
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(), "error");
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(), localization.getText(LocalizationKey.TEXT_LOGIN_ERROR));
        router = new Router(RouterType.FORWARD, RouterPath.LOGIN_PAGE);
      }
      return router;
    } catch (ServiceException serviceException) {
      throw new CommandException("Login command error.", serviceException);
    }
  }
}
