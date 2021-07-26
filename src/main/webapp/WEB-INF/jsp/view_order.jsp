<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_ORDER_USER_PAGE_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="${TEMPLATE_HEADER}" />
        <c:if test="${RESP_FORM_RESULT_STATUS != null}">
            <div class="forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
        </c:if>
        <div class="flex flex__justify-content_space-between orders__item order order_${RESP_ORDER.status}">
            <div class="order-info">
                <h2 class="order-info__route">${RESP_ORDER.route}</h2>
                <pre class="order-info__details">${RESP_ORDER.details}</pre>
            </div>
            <div class="order__actions">
                <!-- Close -->
                <form class="forms" method="post" action="<c:url value='controller?command=close_order' />">
                    <input type="hidden" name="${KEY_ORDER_ID}" value="${RESP_ORDER.id}">
                    <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_CLOSE_ORDER]}" />
                </form>
            </div>
        </div>
        <div class="offers">
            <h2 class="offers__header">${LOCALE[TEXT_ORDER_OFFERS_TITLE]}</h2>
            <div class="flex flex__justify-content_center flex__flex-wrap_wrap">
                <c:forEach var="offer" items="${RESP_ORDER.offers}">
                    <div class="offers__item offer">
                        <span class="offer__header">${offer.user.firstName} ${offer.user.lastName}</span>
                        <span class="offer__price">${offer.price} USD</span>
                        <form class="forms" method="post" action="<c:url value='controller?command=accept_offer' />">
                            <input type="hidden" name="${KEY_ORDER_ID}" value="${RESP_ORDER.id}">
                            <input type="hidden" name="${KEY_OFFER_ID}" value="${offer.id}">
                            <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_ACCEPT_OFFER]}" />
                        </form>
                        <form class="forms" method="post" action="<c:url value='controller?command=decline_offer' />">
                            <input type="hidden" name="${KEY_ORDER_ID}" value="${RESP_ORDER.id}">
                            <input type="hidden" name="${KEY_OFFER_ID}" value="${offer.id}">
                            <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_DECLINE_OFFER]}" />
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="${TEMPLATE_FORMS_ORDER}" />
</body>
</html>
