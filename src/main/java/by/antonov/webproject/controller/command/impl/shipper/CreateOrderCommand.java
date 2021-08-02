package by.antonov.webproject.controller.command.impl.shipper;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_COMMAND;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_ORDER_DETAILS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_ORDER_ROUTE;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_STATUS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_ERROR;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_SUCCESS;
import static by.antonov.webproject.controller.SessionKey.USER_OBJ;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ORDER_NEW_ORDER_ERROR_MESSAGE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ORDER_NEW_ORDER_SUCCESS_MESSAGE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;

public class CreateOrderCommand implements Command {

  private final User.Role[] allowedRole = new Role[]{Role.ADMINISTRATOR, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    try {
      User user = (User) request.getSession().getAttribute(USER_OBJ.name());
      long userId = user.getId();
      String route = request.getParameter(KEY_ORDER_ROUTE.getValue());
      String details = request.getParameter(KEY_ORDER_DETAILS.getValue());
      String status;
      String localizationKey;
      if (orderService.createOrder(route, details, userId)) {
        status = KEY_STYLE_SUCCESS.getValue();
        localizationKey = TEXT_ORDER_NEW_ORDER_SUCCESS_MESSAGE.name();
      } else {
        status = KEY_STYLE_ERROR.getValue();
        localizationKey = TEXT_ORDER_NEW_ORDER_ERROR_MESSAGE.name();
      }
      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        KEY_COMMAND.getValue() + "=open_my_orders",
                        KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    }
  }
}
