package by.antonov.webproject.controller.command;

import by.antonov.webproject.exception.ProjectException;

public class CommandFactory {
  public static Command defineCommand(String commandName)
      throws ProjectException {
    CommandDefinition commandDefinition;
    try {
      commandDefinition = CommandDefinition.valueOf(commandName.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new ProjectException("Incorrect command type " + commandName, e);
    }

    return commandDefinition.getCommand();
  }
}
