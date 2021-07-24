package by.antonov.webproject.controller.command.impl.carrier;

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
import by.antonov.webproject.model.service.OfferService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenOrderForCarrierCommand implements Command {
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.CARRIER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    OfferService offerService = ServiceDefinition.getInstance().getOfferService();
    try {
      User user = (User) request.getSession().getAttribute(SessionKey.USER_OBJ.name());
      long userId = user.getId();
      List<Order> orders = offerService.getOrdersForCarrier(userId);
      request.setAttribute(ResponceKey.RESP_ORDER_RESULT_LIST.name(), orders);
      return new Router(RouterType.FORWARD, RouterPath.CARRIER_ORDERS);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(),serviceException);
    }
  }
}
