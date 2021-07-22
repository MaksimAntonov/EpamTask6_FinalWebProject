package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_FIRST_NAME;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_LAST_NAME;

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

public class ChangeUserNameCommand implements Command {
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
      String firstName = request.getParameter(KEY_USER_FIRST_NAME.getValue());
      String lastName = request.getParameter(KEY_USER_LAST_NAME.getValue());

      UserService userService = ServiceDefinition.getInstance().getUserService();
      String status;
      String localizationKey;
      if (userService.changeUserName(userId, firstName, lastName)) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        status = RequestFieldKey.KEY_STYLE_SUCCESS.getValue();
        localizationKey = LocalizationKey.TEXT_PROFILE_UPDATE_SUCCESS_MESSAGE.name();
      } else {
        status = RequestFieldKey.KEY_STYLE_ERROR.getValue();
        localizationKey = LocalizationKey.TEXT_PROFILE_UPDATE_ERROR_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        RequestFieldKey.KEY_COMMAND.getValue() + "=go_to_profile",
                        RequestFieldKey.KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Update user data", serviceException);
    }
  }
}
