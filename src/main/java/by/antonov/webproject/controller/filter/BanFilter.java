package by.antonov.webproject.controller.filter;

import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.entity.User.Status;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.model.service.UserService;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter( urlPatterns = {"/controller"} )
public class BanFilter implements Filter {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    HttpSession session = httpServletRequest.getSession();
    User user = (User) session.getAttribute(SessionKey.USER_OBJ.name());

    UserService userService = ServiceDefinition.getInstance().getUserService();
    try {
      if (user != null && userService.checkUserStatus(user.getId(), Status.BLOCKED)) {
        session.removeAttribute(SessionKey.USER_OBJ.name());
        session.setAttribute(SessionKey.USER_ROLE.name(), Role.GUEST);
        httpServletResponse.sendRedirect(RouterPath.OPEN_LOGIN_PAGE.getValue() + "&status=error&translate_key=text_profile_user_blocked_message");
      } else {
        filterChain.doFilter(servletRequest, servletResponse);
      }
    } catch (ServiceException serviceException) {
      logger.error("Filter error: {}", serviceException.getMessage());
      throw new ServletException("Project Exception: " + serviceException.getMessage(), serviceException);
    }
  }
}
