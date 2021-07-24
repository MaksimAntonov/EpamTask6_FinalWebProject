package by.antonov.webproject.model.service;

import by.antonov.webproject.model.service.impl.OfferServiceImpl;
import by.antonov.webproject.model.service.impl.OrderServiceImpl;
import by.antonov.webproject.model.service.impl.UserServiceImpl;

public class ServiceDefinition {
  private static ServiceDefinition instance;
  private final OfferService offerService;
  private final OrderService orderService;
  private final UserService userService;

  private ServiceDefinition() {
    this.offerService = new OfferServiceImpl();
    this.orderService = new OrderServiceImpl();
    this.userService = new UserServiceImpl();
  }

  public static ServiceDefinition getInstance() {
    if (instance == null) {
      instance = new ServiceDefinition();
    }

    return instance;
  }

  public OfferService getOfferService() {
    return this.offerService;
  }

  public OrderService getOrderService() {
    return this.orderService;
  }

  public UserService getUserService() {
    return this.userService;
  }
}
