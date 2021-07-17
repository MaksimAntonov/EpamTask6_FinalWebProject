package by.antonov.webproject.controller;

public enum RouterPath implements EnumBase {
  PROJECT_ROOT("/EpamTask6_FinalWebProject_war_exploded/"),
  ERROR_404_PAGE("jsp/error_404.jsp"),
  LOGIN_REGISTRATION_PAGE("jsp/login_registration_page.jsp"),
  ORDER_PAGE("/WEB-INF/jsp/orders.jsp"),

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
