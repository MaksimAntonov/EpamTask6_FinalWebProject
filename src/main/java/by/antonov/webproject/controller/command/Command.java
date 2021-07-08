package by.antonov.webproject.controller.command;

import by.antonov.webproject.exception.ProjectException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
  Router execute(HttpServletRequest request, HttpServletResponse response)
      throws ProjectException;
}
