package by.antonov.webproject.model.dao;

public final class DatabaseColumnName {

  public static final String COUNT = "count";

  // USERS_LIST
  public static final String USER_ID = "user_id";
  public static final String USER_EMAIL = "user_email";
  public static final String USER_PSWD_HASH = "user_pswd_hash";
  public static final String USER_REGISTRATION_DATE = "user_registration_date";
  public static final String USER_FIRST_NAME = "user_first_name";
  public static final String USER_LAST_NAME = "user_last_name";
  public static final String USER_PHONE = "user_phone";

  // USERS_ROLE
  public static final String USER_ROLE_NAME = "role_name";

  // USERS_STATUS
  public static final String USER_STATUS_NAME = "status_name";

  // ORDERS_LIST
  public static final String ORDER_ID = "order_id";
  public static final String ORDER_ROUTE = "order_route";
  public static final String ORDER_DETAILS = "order_details";
  public static final String ORDER_CREATE_DATE = "order_date";
  public static final String ORDER_UPDATE_DATE = "order_update_date";
  public static final String ORDER_STATUS = "order_status";

  // OFFERS_LIST
  public static final String OFFER_ID = "offer_id";
  public static final String OFFER_PRICE = "offer_price";
  public static final String OFFER_DATE = "offer_date";
  public static final String OFFER_STATUS = "offer_status";
}
