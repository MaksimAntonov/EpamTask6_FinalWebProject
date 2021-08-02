package by.antonov.webproject.controller.filter;

import static by.antonov.webproject.controller.SessionKey.CURRENT_LOCALE;
import static by.antonov.webproject.controller.SessionKey.LOCALE;

import by.antonov.webproject.util.localization.Localization;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpSession session = httpServletRequest.getSession();
    String locale = (String) session.getAttribute(CURRENT_LOCALE.name());
    if (locale == null) {
      session.setAttribute(CURRENT_LOCALE.name(), Localization.EN.getLanguageCode());
      session.setAttribute(LOCALE.name(), Localization.EN.getResourceBundle());
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
