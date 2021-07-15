package by.antonov.webproject.controller.command.impl;

import static by.antonov.webproject.controller.RequestFieldKey.*;

import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.LocalizeKey;
import by.antonov.webproject.localization.Localizer;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterUserCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    UserService userService = ServiceDefinition.getInstance().getUserService();
    Router router = null;
    String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    if (currentLocale == null) {
      currentLocale = "en";
    }
    Localizer localizer = Localizer.valueOf(currentLocale.toUpperCase());
    try {
      String email = request.getParameter(KEY_USER_EMAIL.getValue());
      String password = request.getParameter(KEY_USER_PASSWORD.getValue());
      String passwordConfirm = request.getParameter(KEY_USER_PASSWORD_CONFIRM.getValue());
      String firstName = request.getParameter(KEY_USER_FIRST_NAME.getValue());
      String lastName = request.getParameter(KEY_USER_LAST_NAME.getValue());
      String phone = request.getParameter(KEY_USER_PHONE.getValue());
      String role = request.getParameter(KEY_USER_ROLE.getValue());

      Map<ResponceKey, String> resultMap = userService.registerUser(email, password, passwordConfirm, firstName,
                                                                   lastName, phone, role);

      if (resultMap.get(ResponceKey.RESP_REGISTRATION_RESULT_STATUS).equals("success")) {
        resultMap.put(ResponceKey.RESP_REGISTRATION_RESULT_MESSAGE,
                      localizer.getText(LocalizeKey.TEXT_REGISTRATION_SUCCESS_MESSAGE));
      } else {
        if (resultMap.get(ResponceKey.RESP_REGISTRATION_RESULT_STATUS).equals("error")) {
          resultMap.put(ResponceKey.RESP_REGISTRATION_RESULT_MESSAGE,
                        localizer.getText(LocalizeKey.TEXT_REGISTRATION_ERROR_MESSAGE));
        }
        if (resultMap.get(ResponceKey.RESP_REGISTRATION_RESULT_STATUS).equals("insert_error")) {
          resultMap.put(ResponceKey.RESP_REGISTRATION_RESULT_MESSAGE,
                        localizer.getText(LocalizeKey.TEXT_REGISTRATION_INSERT_ERROR_MESSAGE));
          resultMap.put(ResponceKey.RESP_REGISTRATION_RESULT_STATUS, "error");
        }
      }

      for (Map.Entry<ResponceKey, String> mapElement : resultMap.entrySet()) {
        request.setAttribute(mapElement.getKey().name(), mapElement.getValue());
      }

      router = new Router(RouterType.FORWARD, RouterPath.LOGIN_REGISTRATION_PAGE);
    } catch (ServiceException serviceException) {
      throw new ProjectException("Registration command error", serviceException);
    }

    return router;
  }
}
