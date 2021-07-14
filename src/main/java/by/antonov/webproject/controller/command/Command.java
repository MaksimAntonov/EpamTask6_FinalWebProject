package by.antonov.webproject.controller.command;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.exception.ProjectException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
  Router execute(HttpServletRequest request)
      throws ProjectException;
}
