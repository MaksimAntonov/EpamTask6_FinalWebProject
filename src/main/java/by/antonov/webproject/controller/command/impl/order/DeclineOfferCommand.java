package by.antonov.webproject.controller.command.impl.order;

import static by.antonov.webproject.controller.RequestFieldKey.KEY_OFFER_ID;
import static by.antonov.webproject.controller.RequestFieldKey.KEY_ORDER_ID;

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
import by.antonov.webproject.localization.LocalizationKey;
import by.antonov.webproject.model.service.OrderService;
import by.antonov.webproject.model.service.ServiceDefinition;
import jakarta.servlet.http.HttpServletRequest;

public class DeclineOfferCommand implements Command {
  private final User.Role[] allowedRole = new Role[] {Role.ADMINISTRATOR, Role.SHIPPER};

  @Override
  public Router execute(HttpServletRequest request)
      throws CommandException {
    if (!checkUserGroup((User.Role) request.getSession().getAttribute(SessionKey.USER_ROLE.name()), allowedRole)) {
      return new Router(RouterType.REDIRECT, RouterPath.PROJECT_ROOT);
    }

    OrderService orderService = ServiceDefinition.getInstance().getOrderService();
    try {
      long order_id = Long.parseLong(request.getParameter(KEY_ORDER_ID.getValue()));
      long offer_id = Long.parseLong(request.getParameter(KEY_OFFER_ID.getValue()));
      String status;
      String localizationKey;
      if (orderService.declineOffer(offer_id)) {
        status = RequestFieldKey.KEY_STYLE_SUCCESS.getValue();
        localizationKey = LocalizationKey.TEXT_ORDER_DECLINE_SUCCESS_MESSAGE.name();
      } else {
        status = RequestFieldKey.KEY_STYLE_ERROR.getValue();
        localizationKey = LocalizationKey.TEXT_ORDER_DECLINE_ERROR_MESSAGE.name();
      }

      return new Router(RouterType.REDIRECT, RouterPath.CONTROLLER,
                        RequestFieldKey.KEY_COMMAND.getValue() + "=view_order",
                        KEY_ORDER_ID.getValue() + "=" + order_id,
                        RequestFieldKey.KEY_PARAMETER_STATUS.getValue() + "=" + status,
                        RequestFieldKey.KEY_PARAMETER_TRANSLATE_KEY.getValue() + "=" + localizationKey);
    } catch (ServiceException serviceException) {
      throw new CommandException(serviceException.getMessage(), serviceException);
    }
  }
}
