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
import by.antonov.webproject.localization.LocalizationKey;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ViewOrderCommand implements Command {
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    long orderId = Long.parseLong(request.getParameter(RequestFieldKey.KEY_ORDER_ID.getValue()));


    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    try {
      Optional<Order> orderOpt = orderService.getOrderById(orderId);
      if (orderOpt.isPresent()) {
        request.setAttribute(ResponceKey.RESP_ORDER.name(), orderOpt.get());
        return new Router(RouterType.FORWARD, RouterPath.VIEW_ORDER_PAGE);
      } else {
        return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                          RequestFieldKey.KEY_COMMAND.getValue() + "=open_my_orders",
                          RequestFieldKey.KEY_PARAMETER_STATUS.getValue() + "=error",
                          RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + LocalizationKey.TEXT_ORDER_VIEW_ERROR_MESSAGE.name());
      }
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(),serviceException);
    }
  }
}
