package by.antonov.webproject.controller.command.impl;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_LOCALE;
import static by.antonov.webproject.controller.SessionKey.CURRENT_LOCALE;
import static by.antonov.webproject.controller.SessionKey.LOCALE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.util.localization.Localization;
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
      String requiredLocale = request.getParameter(KEY_LOCALE.getValue());
      HttpSession session = request.getSession();
      Localization localization = Localization.valueOf(requiredLocale.toUpperCase());
      session.setAttribute(CURRENT_LOCALE.name(), requiredLocale);
      session.setAttribute(LOCALE.name(), localization.getResourceBundle());
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
