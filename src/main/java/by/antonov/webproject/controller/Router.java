package by.antonov.webproject.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

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

  public Router(RouterType routerType, RouterPath routerPath, String... params) {
    this.routerType = routerType;
    this.routerPath = routerPath.getValue() + "?" + String.join("&", params);
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
