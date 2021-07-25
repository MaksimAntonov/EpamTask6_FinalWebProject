package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.localization.Localization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLocaleCommand implements Command {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    try {
      String requiredLocale = request.getParameter(RequestFieldKey.KEY_LOCALE.getValue());
      HttpSession session = request.getSession();
      Localization localization = Localization.valueOf(requiredLocale.toUpperCase());
      session.setAttribute(SessionKey.CURRENT_LOCALE.name(), requiredLocale);
      session.setAttribute(SessionKey.LOCALE.name(), localization.getResourceBundle());
      String prevPath = request.getParameter("redirect_url");
      if (prevPath == null) {
        prevPath = RouterPath.PROJECT_ROOT.getValue();
      }
      return new Router(RouterType.REDIRECT, prevPath);
    } catch (IllegalArgumentException exception) {
      logger.error("Bad request: {}", exception.getMessage());
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
