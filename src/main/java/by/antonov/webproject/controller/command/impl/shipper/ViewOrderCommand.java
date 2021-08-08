package by.antonov.webproject.controller.command.impl.shipper;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_COMMAND;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_ORDER_ID;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_STATUS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY;
import static by.antonov.webproject.controller.ResponseKey.RESP_ORDER;
import static by.antonov.webproject.controller.SessionKey.USER_OBJ;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ORDER_NOT_AUTHOR;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_ORDER_VIEW_ERROR_MESSAGE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.Order.Status;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewOrderCommand implements Command {

  private static final Logger logger = LogManager.getLogger();
  private final User.Role allowedRole = Role.SHIPPER;

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    String orderIdStr = request.getParameter(KEY_ORDER_ID.getValue());
    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    try {
      User user = (User) request.getSession().getAttribute(USER_OBJ.name());
      long userId = user.getId();
      long orderId = Long.parseLong(orderIdStr);

      Router result;
      if (orderService.checkOrderAuthor(orderId, userId)) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isPresent()) {
          if (orderOpt.get().getStatus() == Status.NEW) {
            request.setAttribute(RESP_ORDER.name(), orderOpt.get());
            result = new Router(RouterType.FORWARD, RouterPath.VIEW_ORDER_PAGE);
          } else {
            result = new Router(RouterType.REDIRECT, RouterPath.OPEN_ORDER_PAGE);
          }
        } else {
          result = new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                            KEY_COMMAND.getValue() + "=open_my_orders",
                            KEY_PARAMETER_STATUS.getValue() + "=error",
                            KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + TEXT_ORDER_VIEW_ERROR_MESSAGE.name());
        }
      } else {
        result = new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                          KEY_COMMAND.getValue() + "=open_my_orders",
                          KEY_PARAMETER_STATUS.getValue() + "=error",
                          KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + TEXT_ORDER_NOT_AUTHOR.name());
      }

      return result;
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, orderId={}", exception.getMessage(), orderIdStr);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
