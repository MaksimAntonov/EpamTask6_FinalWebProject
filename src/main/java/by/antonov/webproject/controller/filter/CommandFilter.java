package by.antonov.webproject.controller.filter;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.CommandDefinition;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller" } )
public class CommandFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    try {
      String command = httpServletRequest.getParameter(RequestFieldKey.KEY_COMMAND.getValue());
      CommandDefinition.valueOf(command.toUpperCase());

      filterChain.doFilter(servletRequest, servletResponse);
    } catch (IllegalArgumentException exception) {
      httpServletResponse.sendRedirect(RouterPath.ERROR_404_PAGE.getValue());
    }
  }
}
