package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.command.impl.AddOrderCommand;
import by.antonov.webproject.controller.command.impl.ChangeLocale;
import by.antonov.webproject.controller.command.impl.GoToLoginRegistrationPage;
import by.antonov.webproject.controller.command.impl.LoginUserCommand;
import by.antonov.webproject.controller.command.impl.OpenOrdersCommand;
import by.antonov.webproject.controller.command.impl.RegisterUserCommand;

public enum CommandDefinition {
  // goto command
  GO_TO_LOGIN_REGISTRATION_PAGE(new GoToLoginRegistrationPage()),
  // open command
  OPEN_ORDERS(new OpenOrdersCommand()),

  // action command
  CHANGE_LOCALE(new ChangeLocale()),
  ADD_ORDER(new AddOrderCommand()),
  LOGIN_USER(new LoginUserCommand()),
  REGISTRATION_USER(new RegisterUserCommand());

  private final Command command;

  CommandDefinition(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return this.command;
  }
}
