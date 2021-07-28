package by.antonov.webproject.controller.command.impl.user;

import by.antonov.webproject.controller.ResponseKey;
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
import by.antonov.webproject.model.service.OfferService;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToProfileCommand implements Command {

  private final User.Role[] allowedRole = new User.Role[]{Role.CARRIER, Role.SHIPPER, Role.ADMINISTRATOR};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      long userId = user.getId();
      if (user.getUserRole() == Role.SHIPPER) {
        OrderService orderService = ServiceDefinition.getInstance().getOrderService();
        List<Order> orders = orderService.getAllOrdersByShipperId(userId, 5);
        request.setAttribute(ResponseKey.RESP_ORDER_RESULT_LIST.name(), orders);
      }
      if (user.getUserRole() == Role.CARRIER) {
        OfferService offerService = ServiceDefinition.getInstance().getOfferService();
        List<Order> orders = offerService.getOrdersForCarrier(userId, 5);
        request.setAttribute(ResponseKey.RESP_ORDER_RESULT_LIST.name(), orders);
      }
      if (user.getUserRole() == Role.ADMINISTRATOR) {
        UserService userService = ServiceDefinition.getInstance().getUserService();
        List<User> users = userService.getUsersList(5);
        request.setAttribute(ResponseKey.RESP_USER_RESULT_LIST.name(), users);
      }
      return new Router(RouterType.FORWARD, RouterPath.PROFILE_PAGE);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    }
  }
}
