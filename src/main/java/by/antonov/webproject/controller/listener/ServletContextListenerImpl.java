package by.antonov.webproject.controller.listener;

import by.antonov.webproject.controller.EnumBase;
import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.util.localization.Localization;
import by.antonov.webproject.util.localization.LocalizationKey;
import by.antonov.webproject.model.connection.ConnectionPool;
import by.antonov.webproject.model.dao.DaoDefinition;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.util.ValidationPattern;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Map;
import java.util.TreeMap;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ConnectionPool.getInstance();
    ServiceDefinition.getInstance();
    DaoDefinition.getInstance();

    setEnumsToContext(sce.getServletContext(), RouterPath.values());
    setEnumsToContext(sce.getServletContext(), LocalizationKey.values());
    setEnumsToContext(sce.getServletContext(), ValidationPattern.values());
    setEnumsToContext(sce.getServletContext(), RequestFieldKey.values());

    setLanguageForSelect(sce.getServletContext(), Localization.values());
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ConnectionPool.getInstance().destroyPool();
  }

  private <T extends Enum<?>> void setEnumsToContext(ServletContext servletContext, T[] enumElements) {
    for (T enumVal : enumElements) {
      servletContext.setAttribute(enumVal.name(), ((EnumBase) enumVal).getValue());
    }
  }

  private void setLanguageForSelect(ServletContext servletContext, Localization[] localizations) {
    Map<String, String> localizationMap = new TreeMap<>();
    for (Localization localization : localizations) {
      localizationMap.put(localization.getLanguageCode(), localization.getLanguageName());
    }
    servletContext.setAttribute(SessionKey.LOCALISATION_LIST.name(), localizationMap);
  }
}
