package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.command.Router;
import by.antonov.webproject.controller.command.RouterType;
import by.antonov.webproject.entity.Order;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.service.OrderService;
import by.antonov.webproject.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenOrdersCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request, HttpServletResponse response)
      throws ProjectException {
    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    //long id = Long.parseLong(request.getSession().getAttribute("USER_ID").toString());
    Router router;
    try {
      List<Order> orders = orderService.findAllOrders();
      request.setAttribute("orderList", orders);
      router = new Router(RouterType.FORWARD, "./WEB-INF/jsp/orders.jsp");
    } catch (ServiceException serviceException) {
      throw new ProjectException(serviceException);
    }
    return router;
  }
}
