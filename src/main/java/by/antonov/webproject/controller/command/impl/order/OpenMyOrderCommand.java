package by.antonov.webproject.controller.command.impl.order;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponceKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.localization.Localization;
import by.antonov.webproject.localization.LocalizationKey;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenMyOrderCommand implements Command {
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    /*String currentLocale = (String) request.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
    Localization localization = Localization.valueOf(currentLocale.toUpperCase());

    String status = request.getParameter(RequestFieldKey.KEY_PARAMETER_STATUS.getValue());

    if (status != null) {
      request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(), status);
      switch (status) {
        case "close_success" -> {
          request.setAttribute(ResponceKey.RESP_FORM_RESULT_STATUS.name(), RequestFieldKey.KEY_STYLE_SUCCESS.getValue());
          request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                               localization.getText(LocalizationKey.TEXT_ORDER_UPDATE_CLOSE_SUCCESS_MESSAGE));
        }
        case "error" -> {
          request.setAttribute(ResponceKey.RESP_FORM_RESULT_MESSAGE.name(),
                               localization.getText(LocalizationKey.TEXT_ORDER_UPDATE_ERROR_MESSAGE));
        }
      }
    }*/

    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      long userId = user.getId();
      List<Order> orders = orderService.getAllOrdersByShipperId(userId);
      request.setAttribute(ResponceKey.RESP_ORDER_RESULT_LIST.name(), orders);
      return new Router(RouterType.FORWARD, RouterPath.MY_ORDER_PAGE);
    } catch (ServiceException serviceException) {
      throw new CommandException(serviceException);
    }
  }
}
