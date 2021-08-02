package by.antonov.webproject.controller.command.impl.carrier;

import by.antonov.webproject.controller.RequestFieldKey;
import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.SessionKey;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OfferService;
import by.antonov.webproject.model.service.ServiceDefinition;
import by.antonov.webproject.util.localization.LocalizationKey;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelOfferCommand implements Command {

  private static final Logger logger = LogManager.getLogger();
  private final User.Role[] allowedRole = new Role[]{Role.ADMINISTRATOR, Role.CARRIER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    long offerId = -1;
    OfferService offerService = ServiceDefinition.getInstance().getOfferService();
    try {
      offerId = Long.parseLong(request.getParameter(RequestFieldKey.KEY_OFFER_ID.getValue()));

      String status;
      String localizationKey;
      if (offerService.cancelOffer(offerId)) {
        status = RequestFieldKey.KEY_STYLE_SUCCESS.getValue();
        localizationKey = LocalizationKey.TEXT_OFFER_CANCEL_OFFER_SUCCESS_MESSAGE.name();
      } else {
        status = RequestFieldKey.KEY_STYLE_ERROR.getValue();
        localizationKey = LocalizationKey.TEXT_OFFER_CANCEL_OFFER_ERROR_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        RequestFieldKey.KEY_COMMAND.getValue() + "=open_carrier_orders",
                        RequestFieldKey.KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, offerId={}", exception.getMessage(), offerId);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
