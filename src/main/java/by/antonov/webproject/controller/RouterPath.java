package by.antonov.webproject.controller;

public enum RouterPath implements EnumBase {
  PROJECT_ROOT("/EpamTask6_FinalWebProject_war_exploded/"),
  ERROR_404_PAGE("jsp/error_404.jsp"),
  CONTROLLER("controller"),
  OPEN_LOGIN_PAGE("controller?command=go_to_login_page"),
  OPEN_REGISTRATION_PAGE("controller?command=go_to_registration_page"),
  OPEN_PROFILE_PAGE("controller?command=go_to_profile"),
  OPEN_ORDER_PAGE("controller?command=open_my_orders"),

  LOGIN_PAGE("/WEB-INF/jsp/login_page.jsp"),
  REGISTRATION_PAGE("/WEB-INF/jsp/registration_page.jsp"),
  PROFILE_PAGE("/WEB-INF/jsp/user_profile.jsp"),
  ORDER_PAGE("/WEB-INF/jsp/orders.jsp"),
  MY_ORDER_PAGE("/WEB-INF/jsp/user_orders.jsp"),

  // Templates
  TEMPLATE_HEAD_DATA("/WEB-INF/jsp/templates/tmpl_head_data.jsp"),
  TEMPLATE_HEADER("/WEB-INF/jsp/templates/tmpl_header.jsp");

  private final String pagePath;

  private RouterPath(String pagePath) {
    this.pagePath = pagePath;
  }

  public String getValue() {
    return this.pagePath;
  }
}
