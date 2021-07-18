package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.command.impl.AddOrderCommand;
import by.antonov.webproject.controller.command.impl.ChangeLocaleCommand;
import by.antonov.webproject.controller.command.impl.ChangeUserNameCommand;
import by.antonov.webproject.controller.command.impl.ChangeUserPasswordCommand;
import by.antonov.webproject.controller.command.impl.ChangeUserPhoneCommand;
import by.antonov.webproject.controller.command.impl.GoToLoginRegistrationPageCommand;
import by.antonov.webproject.controller.command.impl.GoToProfileCommand;
import by.antonov.webproject.controller.command.impl.LoginUserCommand;
import by.antonov.webproject.controller.command.impl.LogoutUserCommand;
import by.antonov.webproject.controller.command.impl.OpenOrdersCommand;
import by.antonov.webproject.controller.command.impl.RegisterUserCommand;

public enum CommandDefinition {
  // goto command
  GO_TO_LOGIN_REGISTRATION_PAGE(new GoToLoginRegistrationPageCommand()),
  GO_TO_PROFILE(new GoToProfileCommand()),
  // open command
  OPEN_ORDERS(new OpenOrdersCommand()),

  // action command
  CHANGE_LOCALE(new ChangeLocaleCommand()),
  LOGIN_USER(new LoginUserCommand()),
  LOGOUT_USER(new LogoutUserCommand()),
  REGISTRATION_USER(new RegisterUserCommand()),
  CHANGE_USER_NAME(new ChangeUserNameCommand()),
  CHANGE_USER_PHONE(new ChangeUserPhoneCommand()),
  CHANGE_USER_PASSWORD(new ChangeUserPasswordCommand()),
  ;
  private final Command command;

  CommandDefinition(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return this.command;
  }
}
