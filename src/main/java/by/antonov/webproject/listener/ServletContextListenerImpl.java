package by.antonov.webproject.listener;

import by.antonov.webproject.connection.ConnectionPool;
import by.antonov.webproject.controller.EnumBase;
import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.dao.DaoDefinition;
import by.antonov.webproject.localization.LocalizeKey;
import by.antonov.webproject.service.ServiceDefinition;
import by.antonov.webproject.util.ValidationPattern;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ConnectionPool.getInstance();
    ServiceDefinition.getInstance();
    DaoDefinition.getInstance();

    setEnumsToContext(sce.getServletContext(), RouterPath.values());
    setEnumsToContext(sce.getServletContext(), LocalizeKey.values());
    setEnumsToContext(sce.getServletContext(), ValidationPattern.values());
    setEnumsToContext(sce.getServletContext(), RequestFieldKey.values());
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
}
