package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_PASSWORD;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_PASSWORD_CONFIRM;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.LocalizationKey;
import by.antonov.webproject.localization.Localization;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeUserPasswordCommand implements Command {
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.CARRIER, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localization localization = Localization.valueOf(currentLocale.toUpperCase());

    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      Long userId = user.getId();
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      String passwordConfirm = request.getParameter(KEY_USER_PASSWORD_CONFIRM.getValue());

      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.changeUserPassword(userId, password, passwordConfirm)) {
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(),
                             RequestFieldKey.KEY_STYLE_SUCCESS.getValue());
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                             localization.getText(LocalizationKey.TEXT_PROFILE_UPDATE_SUCCESS_MESSAGE));
      } else {
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(),
                             RequestFieldKey.KEY_STYLE_ERROR.getValue());
        request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                             localization.getText(LocalizationKey.TEXT_PROFILE_UPDATE_ERROR_MESSAGE));
      }

      return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
    } catch (ServiceException serviceException) {
      throw new CommandException("Update user data", serviceException);
    }
  }
}
