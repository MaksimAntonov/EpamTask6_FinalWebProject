package by.antonov.webproject.controller;

public class Router {
  private final RouterType routerType;
  private final String routerPath;

  public Router(RouterType routerType, RouterPath routerPath) {
    this.routerType = routerType;
    this.routerPath = routerPath.getValue();
  }

  public Router(RouterType routerType, String routerPath) {
    this.routerType = routerType;
    this.routerPath = routerPath;
  }

  public RouterType getRouterType() {
    return routerType;
  }

  public String getRouterPath() {
    return routerPath;
  }

  public enum RouterType {
    FORWARD,
    REDIRECT
  }
}
