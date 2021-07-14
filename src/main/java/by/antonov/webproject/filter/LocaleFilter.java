package by.antonov.webproject.filter;

import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.localization.Localizer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(urlPatterns = { "/controller" })
public class LocaleFilter implements Filter {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpSession session = httpServletRequest.getSession();
    String locale = (String) session.getAttribute(SessionKey.CURRENT_LOCALE.name());
    if (locale == null) {
      session.setAttribute(SessionKey.CURRENT_LOCALE.name(), "en");
      session.setAttribute(SessionKey.LOCALE.name(), Localizer.EN.getResourceBundle());
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
