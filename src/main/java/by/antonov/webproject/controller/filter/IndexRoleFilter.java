package by.antonov.webproject.controller.filter;

import static by.antonov.webproject.controller.SessionKey.USER_ROLE;

import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/index.jsp"})
public class IndexRoleFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpSession session = httpServletRequest.getSession();
    User.Role role = (Role) session.getAttribute(USER_ROLE.name());

    if (role == Role.ADMINISTRATOR || role == Role.SHIPPER || role == Role.CARRIER) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
      httpServletResponse.sendRedirect(RouterPath.OPEN_PROFILE_PAGE.getValue());
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
