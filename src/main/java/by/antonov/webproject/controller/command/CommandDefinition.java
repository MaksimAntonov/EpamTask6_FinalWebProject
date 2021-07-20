package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.command.impl.ChangeLocaleCommand;
import by.antonov.webproject.controller.command.impl.GoToRegistrationPageCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserNameCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserPasswordCommand;
import by.antonov.webproject.controller.command.impl.user.ChangeUserPhoneCommand;
import by.antonov.webproject.controller.command.impl.GoToLoginPageCommand;
import by.antonov.webproject.controller.command.impl.GoToProfileCommand;
import by.antonov.webproject.controller.command.impl.user.LoginUserCommand;
import by.antonov.webproject.controller.command.impl.user.LogoutUserCommand;
import by.antonov.webproject.controller.command.impl.OpenOrdersCommand;
import by.antonov.webproject.controller.command.impl.user.RegisterUserCommand;

public enum CommandDefinition {
  // goto command
  GO_TO_LOGIN_PAGE(new GoToLoginPageCommand()),
  GO_TO_REGISTRATION_PAGE(new GoToRegistrationPageCommand()),
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
