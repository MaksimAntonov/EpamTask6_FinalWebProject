package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_ID;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_PASSWORD;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_PASSWORD_CONFIRM;

import by.antonov.webproject.controller.RequestFieldKey;
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
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeUserPasswordCommand implements Command {
  private static final Logger logger = LogManager.getLogger();
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.CARRIER, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    long userId = -1;
    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      if (user.getUserRole() == Role.ADMINISTRATOR && request.getParameter(KEY_USER_ID.getValue()) != null) {
        userId = Long.parseLong(request.getParameter(KEY_USER_ID.getValue()));
      } else {
        userId = user.getId();
      }
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      String passwordConfirm = request.getParameter(KEY_USER_PASSWORD_CONFIRM.getValue());

      UserService userService = ServiceDefinition.getInstance().getUserService();
      String status;
      String localizationKey;
      if (userService.changeUserPassword(userId, password, passwordConfirm)) {
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
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, userId={}", exception.getMessage(), userId);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
