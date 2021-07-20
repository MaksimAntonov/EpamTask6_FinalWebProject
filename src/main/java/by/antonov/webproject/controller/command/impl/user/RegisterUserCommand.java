package by.antonov.webproject.controller.command.impl.user;

import static by.antonov.webproject.controller.RequestFieldKey.*;

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
import java.util.Map;

public class RegisterUserCommand implements Command {
  private final User.Role allowedRole = Role.GUEST;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localization localization = Localization.valueOf(currentLocale.toUpperCase());
    try {
      String email = request.getParameter(KEY_USER_EMAIL.getValue());
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      String passwordConfirm = request.getParameter(KEY_USER_PASSWORD_CONFIRM.getValue());
      String firstName = request.getParameter(KEY_USER_FIRST_NAME.getValue());
      String lastName = request.getParameter(KEY_USER_LAST_NAME.getValue());
      String phone = request.getParameter(KEY_USER_PHONE.getValue());
      String role = request.getParameter(KEY_USER_ROLE.getValue());

      UserService userService = ServiceDefinition.getInstance().getUserService();
      Map<ResponceKey, String> resultMap = userService.registerUser(email, password, passwordConfirm, firstName,
                                                                   lastName, phone, role);

      Router router;
      if (resultMap.get(ResponceKey.RESP_FORM_RESULT_STATUS).equals(KEY_STYLE_SUCCESS.getValue())) {
        resultMap.put(ResponceKey.RESP_FORM_RESULT_MESSAGE,
                      localization.getText(LocalizationKey.TEXT_REGISTRATION_SUCCESS_MESSAGE));
        router = new Router(RouterType.FORWARD, RouterPath.LOGIN_PAGE);
      } else {
        if (resultMap.get(ResponceKey.RESP_FORM_RESULT_STATUS).equals(RequestFieldKey.KEY_STYLE_ERROR.getValue())) {
          resultMap.put(ResponceKey.RESP_FORM_RESULT_MESSAGE,
                        localization.getText(LocalizationKey.TEXT_REGISTRATION_ERROR_MESSAGE));
        }
        if (resultMap.get(ResponceKey.RESP_FORM_RESULT_STATUS).equals(KEY_STYLE_INSERT_ERROR.getValue())) {
          resultMap.put(ResponceKey.RESP_FORM_RESULT_MESSAGE,
                        localization.getText(LocalizationKey.TEXT_REGISTRATION_INSERT_ERROR_MESSAGE));
          resultMap.put(ResponceKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
        }
        if (resultMap.get(ResponceKey.RESP_FORM_RESULT_STATUS).equals(KEY_STYLE_INSERT_DUPLICATE_ERROR.getValue())) {
          resultMap.put(ResponceKey.RESP_FORM_RESULT_MESSAGE,
                        localization.getText(LocalizationKey.TEXT_REGISTRATION_INSERT_DUPLICATE_ERROR_MESSAGE));
          resultMap.put(ResponceKey.RESP_FORM_RESULT_STATUS, RequestFieldKey.KEY_STYLE_ERROR.getValue());
        }
        router = new Router(RouterType.FORWARD, RouterPath.REGISTRATION_PAGE);
      }

      for (Map.Entry<ResponceKey, String> mapElement : resultMap.entrySet()) {
        request.setAttribute(mapElement.getKey().name(), mapElement.getValue());
      }

      return router;
    } catch (ServiceException serviceException) {
      throw new CommandException("Registration command error: " + serviceException.getMessage(), serviceException);
    }
  }
}
