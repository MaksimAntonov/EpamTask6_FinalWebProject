package by.antonov.webproject.controller.command.impl.admin;

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

public class BanUserCommand implements Command {
  private static final Logger logger = LogManager.getLogger();
  private final User.Role allowedRole = Role.ADMINISTRATOR;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_403_PAGE);
    }

    long userId = -1;
    try {
      userId = Long.parseLong(request.getParameter(RequestFieldKey.KEY_USER_ID.getValue()));
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());

      if (user.getId() == userId) {
        return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
      }

      String status;
      String localizationKey;
      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.banUser(userId)) {
        status = RequestFieldKey.KEY_STYLE_SUCCESS.getValue();
        localizationKey = LocalizationKey.TEXT_ADMIN_USERS_SUCCESS_BAN_MESSAGE.name();
      } else {
        status = RequestFieldKey.KEY_STYLE_ERROR.getValue();
        localizationKey = LocalizationKey.TEXT_ADMIN_USERS_ERROR_BAN_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        RequestFieldKey.KEY_COMMAND.getValue() + "=users_list",
                        RequestFieldKey.KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(),serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, userId={}", exception.getMessage(), userId);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
