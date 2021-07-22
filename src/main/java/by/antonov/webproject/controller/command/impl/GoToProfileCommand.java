package by.antonov.webproject.controller.command.impl;

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
import by.antonov.webproject.localization.Localization;
import by.antonov.webproject.localization.LocalizationKey;
import jakarta.servlet.http.HttpServletRequest;

public class GoToProfileCommand implements Command {
  private final User.Role[] allowedRole = new User.Role[] { Role.CARRIER, Role.SHIPPER, Role.ADMINISTRATOR };

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

   /* String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localization localization = Localization.valueOf(currentLocale.toUpperCase());

    String status = request.getParameter(RequestFieldKey.KEY_PARAMETER_STATUS.getValue());
    if (status != null) {
      request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(), status);
      switch (status) {
        case "success" -> {
          request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                               localization.getText(LocalizationKey.TEXT_PROFILE_UPDATE_SUCCESS_MESSAGE));
        }
        case "error" -> {
          request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                               localization.getText(LocalizationKey.TEXT_PROFILE_UPDATE_ERROR_MESSAGE));
        }
      }
    }*/

    return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
  }
}
