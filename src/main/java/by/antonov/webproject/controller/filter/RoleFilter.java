package by.antonov.webproject.controller.filter;

import static by.antonov.webproject.controller.SessionKey.USER_ROLE;

import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
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
public class RoleFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpSession session = httpServletRequest.getSession();
    User.Role role = (Role) session.getAttribute(USER_ROLE.name());

    if (role == null) {
      session.setAttribute(USER_ROLE.name(), Role.GUEST);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
