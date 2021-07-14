package by.antonov.webproject.controller;

public enum RouterPath implements EnumBase {
  LOGIN_REGISTRATION_PAGE("jsp/login_registration_page.jsp"),
  ORDER_PAGE("/WEB-INF/jsp/orders.jsp"),

  // Templates
  TEMPLATE_HEAD_DATA("/WEB-INF/jsp/templates/tmpl_head_data.jsp"),
  TEMPLATE_HEADER("/WEB-INF/jsp/templates/tmpl_header.jsp");

  String pagePath;

  RouterPath(String pagePath) {
    this.pagePath = pagePath;
  }

  public String getValue() {
    return this.pagePath;
  }
}
