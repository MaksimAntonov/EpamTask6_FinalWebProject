package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddOrderCommand implements Command {

  @Override
  public Router execute(HttpServletRequest request, HttpServletResponse response) {
    return null;
  }
}
