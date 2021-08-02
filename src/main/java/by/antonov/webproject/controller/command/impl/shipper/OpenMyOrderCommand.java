package by.antonov.webproject.controller.command.impl.shipper;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_CURRENT_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_MAX_PAGE;
import static by.antonov.webproject.controller.ResponseKey.RESP_ORDER_RESULT_LIST;
import static by.antonov.webproject.controller.SessionKey.USER_OBJ;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenMyOrderCommand implements Command {

  private static final Logger logger = LogManager.getLogger();
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
      int limit = 10;

      String pageString = Optional.ofNullable(request.getParameter(KEY_PAGE.getValue()))
                                  .orElse("1");
      int currentPage = Integer.parseInt(pageString);

      double countOfOrders = orderService.getCountOfShipperOrders(userId);
      int maxPage = (int) Math.ceil(countOfOrders / limit);

      if (maxPage < currentPage) {
        currentPage = maxPage;
      }

      List<Order> orders = orderService.getAllOrdersByShipperId(userId, currentPage, limit);
      request.setAttribute(RESP_ORDER_RESULT_LIST.name(), orders);
      request.setAttribute(RESP_CURRENT_PAGE.name(), currentPage);
      request.setAttribute(RESP_MAX_PAGE.name(), maxPage);
      return new Router(RouterType.FORWARD, RouterPath.MY_ORDER_PAGE);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}", exception.getMessage());
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
