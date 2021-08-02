package by.antonov.webproject.controller;

import by.antonov.webproject.controller.listener.ContextValue;

public enum RouterPath implements ContextValue {
  PROJECT_ROOT("/EpamTask6_FinalWebProject_war_exploded/"),
  ERROR_400_PAGE("jsp/error_400.jsp"),
  ERROR_403_PAGE("jsp/error_403.jsp"),
  ERROR_404_PAGE("jsp/error_404.jsp"),
  CONTROLLER("controller"),
  OPEN_LOGIN_PAGE("controller?command=go_to_login_page"),
  OPEN_REGISTRATION_PAGE("controller?command=go_to_registration_page"),
  OPEN_PROFILE_PAGE("controller?command=go_to_profile"),
  OPEN_ORDER_PAGE("controller?command=open_my_orders"),

  LOGIN_PAGE("/WEB-INF/jsp/user/login_page.jsp"),
  REGISTRATION_PAGE("/WEB-INF/jsp/user/registration_page.jsp"),
  PROFILE_PAGE("/WEB-INF/jsp/user/user_profile.jsp"),
  MY_ORDER_PAGE("/WEB-INF/jsp/shipper/user_orders.jsp"),
  VIEW_ORDER_PAGE("/WEB-INF/jsp/shipper/view_order.jsp"),
  CARRIER_ORDERS("/WEB-INF/jsp/carrier/carrier_orders.jsp"),
  USERS_LIST("/WEB-INF/jsp/admin/users_list.jsp"),

  // Templates
  TEMPLATE_HEAD_DATA("/WEB-INF/jsp/templates/tmpl_head_data.jsp"),
  TEMPLATE_HEADER("/WEB-INF/jsp/templates/tmpl_header.jsp"),
  TEMPLATE_FORMS_ORDER("/WEB-INF/jsp/templates/tmpl_forms_order.jsp"),
  TEMPLATE_FORMS_PROFILE("/WEB-INF/jsp/templates/tmpl_forms_profile.jsp"),
  TEMPLATE_ORDERS_FOR_SHIPPER_BLOCK("/WEB-INF/jsp/templates/tmpl_orders_for_shipper_block.jsp"),
  TEMPLATE_ORDERS_FOR_CARRIER_BLOCK("/WEB-INF/jsp/templates/tmpl_orders_for_carrier_block.jsp"),
  TEMPLATE_USERS_LIST_TABLE_BLOCK("/WEB-INF/jsp/templates/tmpl_users_list_table_block.jsp"),
  ;
  private final String pagePath;

  RouterPath(String pagePath) {
    this.pagePath = pagePath;
  }

  public String getValue() {
    return this.pagePath;
  }
}
