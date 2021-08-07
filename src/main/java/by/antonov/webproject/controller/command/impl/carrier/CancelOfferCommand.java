package by.antonov.webproject.controller.command.impl.carrier;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_COMMAND;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_OFFER_ID;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_STATUS;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_ERROR;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_STYLE_SUCCESS;
import static by.antonov.webproject.controller.SessionKey.USER_ROLE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_OFFER_CANCEL_OFFER_ERROR_MESSAGE;
import static by.antonov.webproject.util.localization.LocalizationKey.TEXT_OFFER_CANCEL_OFFER_SUCCESS_MESSAGE;

import by.antonov.webproject.controller.Router;
import by.antonov.webproject.controller.Router.RouterType;
import by.antonov.webproject.controller.RouterPath;
import by.antonov.webproject.controller.command.Command;
import by.antonov.webproject.entity.User;
import by.antonov.webproject.entity.User.Role;
import by.antonov.webproject.exception.CommandException;
import by.antonov.webproject.exception.ServiceException;
import by.antonov.webproject.model.service.OfferService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelOfferCommand implements Command {

  private static final Logger logger = LogManager.getLogger();
  private final User.Role[] allowedRole = new Role[]{Role.ADMINISTRATOR, Role.CARRIER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    String offerIdStr = request.getParameter(KEY_OFFER_ID.getValue());
    OfferService offerService = ServiceDefinition.getInstance().getOfferService();
    try {
      long offerId = Long.parseLong(offerIdStr);

      String status;
      String localizationKey;
      if (offerService.cancelOffer(offerId)) {
        status = KEY_STYLE_SUCCESS.getValue();
        localizationKey = TEXT_OFFER_CANCEL_OFFER_SUCCESS_MESSAGE.name();
      } else {
        status = KEY_STYLE_ERROR.getValue();
        localizationKey = TEXT_OFFER_CANCEL_OFFER_ERROR_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        KEY_COMMAND.getValue() + "=open_carrier_orders",
                        KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException("Command exception: " + serviceException.getMessage(), serviceException);
    } catch (NumberFormatException exception) {
      logger.error("Bad request: {}, offerId={}", exception.getMessage(), offerIdStr);
      return new Router(RouterType.REDIRECT, RouterPath.ERROR_400_PAGE);
    }
  }
}
