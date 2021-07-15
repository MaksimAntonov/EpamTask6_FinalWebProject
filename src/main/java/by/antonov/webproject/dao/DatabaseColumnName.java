package by.antonov.webproject.dao;

public final class DatabaseColumnName {
  // USERS_LIST
  public static final String USER_ID = "user_id";
  public static final String USER_EMAIL = "user_email";
  public static final String USER_PSWD_HASH = "user_pswd_hash";
  public static final String USER_PSWD_SALT = "user_pswd_salt";
  public static final String USER_REGISTRATION_DATE = "user_registration_date";
  public static final String USER_FIRST_NAME = "user_first_name";
  public static final String USER_LAST_NAME = "user_last_name";
  public static final String USER_PHONE = "user_phone";
  public static final String USER_ROLE_ID = "user_role_id";
  public static final String USER_STATUS_ID = "user_status_id";

  // USERS_ROLE
  public static final String ROLE_ID = "role_id";
  public static final String USER_ROLE_NAME = "role_name";

  // USERS_STATUS
  public static final String USER_STATUS_NAME = "status_name";

  // ORDERS_LIST
  public static final String ORDER_ID = "order_id";
  public static final String ORDER_DETAILS = "order_details";
  public static final String ORDER_READY_DATE = "order_ready_date";
  public static final String ORDER_END_DATE = "order_end_date";
  public static final String ORDER_SHIPPER_ID = "order_shipper_id";
  public static final String ORDER_STATUS = "order_status";

  // OFFERS_LIST
  public static final String OFFER_ID = "offer_id";
  public static final String OFFER_PRICE = "offer_price";
  public static final String OFFER_DATE = "offer_date";
  public static final String OFFER_ORDER_ID = "offer_order_id";
  public static final String OFFER_CARRIER_ID = "offer_carrier_id";
  public static final String OFFER_STATUS = "offer_status";
}
