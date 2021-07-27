package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.command.impl.ChangeLocaleCommand;
import by.antonov.webproject.controller.command.impl.admin.BanUserCommand;
import by.antonov.webproject.controller.command.impl.admin.OpenUsersListCommand;
import by.antonov.webproject.controller.command.impl.admin.UnBanUserCommand;
import by.antonov.webproject.controller.command.impl.carrier.CancelOfferCommand;
import by.antonov.webproject.controller.command.impl.carrier.MakeOfferCommand;
import by.antonov.webproject.controller.command.impl.carrier.OpenOrderForCarrierCommand;
import by.antonov.webproject.controller.command.impl.shipper.AcceptOfferCommand;
import by.antonov.webproject.controller.command.impl.shipper.CloseOrderCommand;
import by.antonov.webproject.controller.command.impl.shipper.CreateOrderCommand;
import by.antonov.webproject.controller.command.impl.shipper.DeclineOfferCommand;
import by.antonov.webproject.controller.command.impl.shipper.OpenMyOrderCommand;
import by.antonov.webproject.controller.command.impl.shipper.ViewOrderCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserNameCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserPasswordCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserPhoneCommand;
import by.antonov.webproject.controller.command.impl.user.GoToLoginPageCommand;
import by.antonov.webproject.controller.command.impl.user.GoToProfileCommand;
import by.antonov.webproject.controller.command.impl.user.GoToRegistrationPageCommand;
import by.antonov.webproject.controller.command.impl.user.LoginUserCommand;
import by.antonov.webproject.controller.command.impl.user.LogoutUserCommand;
import by.antonov.webproject.controller.command.impl.user.RegisterUserCommand;

public enum CommandDefinition {
  // goto command
  GO_TO_LOGIN_PAGE(new GoToLoginPageCommand()),
  GO_TO_REGISTRATION_PAGE(new GoToRegistrationPageCommand()),
  GO_TO_PROFILE(new GoToProfileCommand()),

  // open command
  OPEN_MY_ORDERS(new OpenMyOrderCommand()),
  VIEW_ORDER(new ViewOrderCommand()),
  OPEN_CARRIER_ORDERS(new OpenOrderForCarrierCommand()),
  USERS_LIST(new OpenUsersListCommand()),

  // action command
  CHANGE_LOCALE(new ChangeLocaleCommand()),
  LOGIN_USER(new LoginUserCommand()),
  LOGOUT_USER(new LogoutUserCommand()),
  REGISTRATION_USER(new RegisterUserCommand()),
  CHANGE_USER_NAME(new ChangeUserNameCommand()),
  CHANGE_USER_PHONE(new ChangeUserPhoneCommand()),
  CHANGE_USER_PASSWORD(new ChangeUserPasswordCommand()),
  CLOSE_ORDER(new CloseOrderCommand()),
  CREATE_ORDER(new CreateOrderCommand()),
  ACCEPT_OFFER(new AcceptOfferCommand()),
  DECLINE_OFFER(new DeclineOfferCommand()),
  MAKE_OFFER(new MakeOfferCommand()),
  CANCEL_OFFER(new CancelOfferCommand()),
  BAN_USER(new BanUserCommand()),
  UNBAN_USER(new UnBanUserCommand());
  private final Command command;

  CommandDefinition(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return this.command;
  }
}
