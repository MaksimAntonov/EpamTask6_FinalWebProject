package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenOrdersCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    Router router;
    try {
      List<Order> orders = orderService.findAllOrders();
      request.setAttribute("orderList", orders);
      router = new Router(RouterType.FORWARD, RouterPath.ORDER_PAGE);
    } catch (ServiceException serviceException) {
      throw new CommandException(serviceException);
    }
    return router;
  }
}
