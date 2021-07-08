package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.command.impl.AddOrderCommand;
import by.antonov.webproject.controller.command.impl.OpenOrdersCommand;

public enum CommandDefinition {
  // open command
  OPEN_ORDERS(new OpenOrdersCommand()),

  // action command
  ADD_ORDER(new AddOrderCommand());

  private final Command command;

  CommandDefinition(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return this.command;
  }
}
