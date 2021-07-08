package by.antonov.webproject.controller;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.controller.command.CommandFactory;
import by.antonov.webproject.controller.command.Router;
import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.service.ServiceDefinition;
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
  public void destroy() {
    ConnectionPool.getInstance().destroyPool();
  }

  @Override
  public void init() {
    ConnectionPool.getInstance();
    ServiceDefinition.getInstance();
    DaoDefinition.getInstance();
  }

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

  private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
    try {
      String commandName = req.getParameter("command");
      Command command = CommandFactory.defineCommand(commandName);
      Router router = command.execute(req, resp);
      switch (router.getRouterType()) {
        case FORWARD -> {
          req.getRequestDispatcher(router.getRouterPath()).forward(req, resp);
        }
        case REDIRECT -> {
          resp.sendRedirect(router.getRouterPath());
        }
        default -> {
          logger.error("Controller: wrong RouterType {}", router.getRouterType());
          resp.sendRedirect("");
        }
      }
    } catch (Exception e) {
      e.fillInStackTrace();
    }
  }
}
