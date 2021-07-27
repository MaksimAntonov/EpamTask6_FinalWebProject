package by.antonov.webproject.controller.filter;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.ResponseKey;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.localization.Localization;
import by.antonov.webproject.localization.LocalizationKey;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class UserFeedbackFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    String feedbackStatus = httpRequest.getParameter(RequestFieldKey.KEY_PARAMETER_STATUS.getValue());
    if (feedbackStatus != null) {
      String currentLocale = (String) httpRequest.getSession().getAttribute(SessionKey.CURRENT_LOCALE.name());
      Localization localization = Localization.valueOf(currentLocale.toUpperCase());
      String localizationKeyReq = httpRequest.getParameter(RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue());
      if (localizationKeyReq != null) {
        LocalizationKey localizationKey = LocalizationKey.valueOf(localizationKeyReq.toUpperCase());

        httpRequest.setAttribute(ResponseKey.RESP_FORM_RESULT_STATUS.name(), feedbackStatus);
        httpRequest.setAttribute(ResponseKey.RESP_FORM_RESULT_MESSAGE.name(), localization.getText(localizationKey));
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
