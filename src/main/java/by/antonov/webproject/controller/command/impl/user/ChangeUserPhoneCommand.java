package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_FIRST_NAME;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_LAST_NAME;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_USER_PHONE;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.LocalizeKey;
import by.antonov.webproject.localization.Localizer;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeUserPhoneCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localizer localizer = Localizer.valueOf(currentLocale.toUpperCase());

    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      Long userId = user.getId();
      String phone = request.getParameter(KEY_USER_PHONE.getValue());

      UserService userService = ServiceDefinition.getInstance().getUserService();
      if (userService.changeUserPhone(userId, phone)) {
        user.setPhone(phone);
        request.setAttribute(ResponceKey.RESP_PROFILE_UPDATE_RESULT_STATUS.name(),
                             RequestFieldKey.KEY_STYLE_SUCCESS.getValue());
        request.setAttribute(ResponceKey.RESP_PROFILE_UPDATE_RESULT_MESSAGE.name(),
                             localizer.getText(LocalizeKey.TEXT_PROFILE_UPDATE_SUCCESS_MESSAGE));
      } else {
        request.setAttribute(ResponceKey.RESP_PROFILE_UPDATE_RESULT_STATUS.name(),
                             RequestFieldKey.KEY_STYLE_ERROR.getValue());
        request.setAttribute(ResponceKey.RESP_PROFILE_UPDATE_RESULT_MESSAGE.name(),
                             localizer.getText(LocalizeKey.TEXT_PROFILE_UPDATE_ERROR_MESSAGE));
      }

      return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
    } catch (ServiceException serviceException) {
      throw new ProjectException("Update user data", serviceException);
    }
  }
}
