package by.antonov.webproject.controller.listener;

/**
 * Interface for enums, which will added into servlet context.
 */
public interface ContextValue {

  /**
   * Get value for Enum. Using for ContextListener
   *
   * @return string value for enum.
   */
  String getValue();
}
