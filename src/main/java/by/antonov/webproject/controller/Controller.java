package by.antonov.webproject.controller;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_COMMAND;

import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.command.CommandFactory;
import by.antonov.webproject.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/controller")
public class Controller extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      String commandName = request.getParameter(KEY_COMMAND.getValue());
      Command command = CommandFactory.defineCommand(commandName);
      Router router = command.execute(request);
      switch (router.getRouterType()) {
        case FORWARD -> request.getRequestDispatcher(router.getRouterPath()).forward(request, response);
        case REDIRECT -> response.sendRedirect(router.getRouterPath());
        default -> {
          logger.error("Controller: wrong RouterType {}", router.getRouterType());
          response.sendRedirect(RouterPath.ERROR_404_PAGE.getValue());
        }
      }
    } catch (CommandException commandException) {
      throw new ServletException("Project Exception: " + commandException.getMessage(), commandException);
    }
  }
}
