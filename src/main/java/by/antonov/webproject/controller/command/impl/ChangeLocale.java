package by.antonov.webproject.controller.command.impl;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.exception.ProjectException;
import by.antonov.webproject.localization.Localizer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocale implements Command {

  @Override
  public Router execute(HttpServletRequest request)
      throws ProjectException {
    String requiredLocale = request.getParameter("locale");
    HttpSession session = request.getSession();
    Localizer localizer = Localizer.valueOf(requiredLocale.toUpperCase());
    session.setAttribute(SessionKey.CURRENT_LOCALE.name(), requiredLocale);
    session.setAttribute(SessionKey.LOCALE.name(), localizer.getResourceBundle());
    String prevPath = request.getParameter("redirect_url");
    session.removeAttribute(SessionKey.REQUIRED_URI.name());
    return new Router(RouterType.REDIRECT, prevPath);
  }
}
