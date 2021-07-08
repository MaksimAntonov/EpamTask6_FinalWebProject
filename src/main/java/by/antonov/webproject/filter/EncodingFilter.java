package by.antonov.webproject.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam (name = "encoding", value = "UTF-8") })
public class EncodingFilter implements Filter {
  private String code;

  @Override
  public void init(FilterConfig filterConfig)
      throws ServletException {
    this.code = filterConfig.getInitParameter("encoding");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String codeRequest = servletRequest.getCharacterEncoding();
    if (this.code != null && !this.code.equalsIgnoreCase(codeRequest)) {
      servletRequest.setCharacterEncoding(this.code);
      servletResponse.setCharacterEncoding(this.code);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    this.code = null;
  }
}
