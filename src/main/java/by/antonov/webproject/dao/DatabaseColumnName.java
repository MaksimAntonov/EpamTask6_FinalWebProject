package by.antonov.webproject.dao;

public final class DatabaseColumnName {
  // USERS_LIST
  public static final String USER_ID = "id";
  public static final String USER_LOGIN = "login";
  public static final String USER_PSWD_HASH = "pswd_hash";
  public static final String USER_PSWD_SALT = "pswd_salt";
  public static final String USER_EMAIL = "email";
  public static final String USER_REGISTRATION_DATE = "registration_date";
  public static final String USER_ROLE_ID = "role_id";
  public static final String USER_STATUS_ID = "status_id";

  // USERS_ROLE
  public static final String ROLE_ID = "id";
  public static final String ROLE_NAME = "role";

  // USERS_STATUS
  public static final String STATUS_ID = "id";
  public static final String STATUS_NAME = "status";

  // ORDERS_LIST
  public static final String ORDER_ID = "id";
  public static final String ORDER_DETAILS = "details";
  public static final String ORDER_READY_DATE = "ready_date";
  public static final String ORDER_END_DATE = "end_date";
  public static final String ORDER_SHIPPER_ID = "shipper_id";

  // OFFERS_LIST
  public static final String OFFER_ID = "id";
  public static final String OFFER_PRICE = "price";
  public static final String OFFER_DATE = "offer_date";
  public static final String OFFER_ORDER_ID = "order_id";
  public static final String OFFER_CARRIER_ID = "carrier_id";
}
