package by.antonov.webproject.controller.command;

import by.antonov.webproject.exception.CommandException;

public class CommandFactory {
  public static Command defineCommand(String commandName)
      throws CommandException {
    CommandDefinition commandDefinition;
    try {
      commandDefinition = CommandDefinition.valueOf(commandName.toUpperCase());

      return commandDefinition.getCommand();
    } catch (IllegalArgumentException e) {
      throw new CommandException("Incorrect command type: " + commandName, e);
    }
  }
}
