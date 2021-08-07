package by.antonov.webproject.controller.command.impl.admin;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_COMMAND;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_STATUS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_ERROR;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_SUCCESS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_ID;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ADMIN_USERS_ERROR_UNBAN_MESSAGE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ADMIN_USERS_SUCCESS_UNBAN_MESSAGE;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnBlockUserCommand implements Command {

  private static final Logger logger = LogManager.getLogger();
  private final User.Role allowedRole = Role.ADMINISTRATOR;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_403_PAGE);
    }

    String userIdStr = request.getParameter(KEY_USER_ID.getValue());
    try {
      long userId = Long.parseLong(userIdStr);

      String status;
      String localizationKey;
      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.unblockUser(userId)) {
        status = KEY_STYLE_SUCCESS.getValue();
        localizationKey = TEXT_ADMIN_USERS_SUCCESS_UNBAN_MESSAGE.name();
      } else {
        status = KEY_STYLE_ERROR.getValue();
        localizationKey = TEXT_ADMIN_USERS_ERROR_UNBAN_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        KEY_COMMAND.getValue() + "=users_list",
                        KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, userId={}", exception.getMessage(), userIdStr);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
