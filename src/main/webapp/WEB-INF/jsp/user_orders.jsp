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
        <div class="orders">
            <c:if test="${RESP_FORM_RESULT_STATUS != null}">
                <div class="user-profile__result user-profile__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
            </c:if>
            <c:forEach var="order" items="${RESP_ORDER_RESULT_LIST}" >
                <div class="flex flex__justify-content_space-between orders__item order order_${order.status}">
                    <div class="order-info">
                        <h2 class="order-info__route">${order.route}</h2>
                        <pre class="order-info__details">${order.details}</pre>
                    </div>
                    <div class="flex flex__align-items_flex-start">
                        <c:choose>
                            <c:when test="${order.status == 'NEW'}">
                                <c:if test="${order.bestOffer != null}" >
                                    <div class="offer">
                                        <span class="offer__header">${LOCALE[TEXT_OFFER_BEST_OFFER_FROM]} (${order.offers.size()})</span>
                                        <span class="offer__carrier-name">${order.bestOffer.user.firstName} ${order.bestOffer.user.lastName}</span>
                                        <span class="offer__carrier-price">${order.bestOffer.price} USD</span>
                                        <form class="forms" method="post" action="<c:url value='controller?command=accept_offer' />">
                                            <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                            <input type="hidden" name="${KEY_OFFER_ID}" value="${order.bestOffer.id}">
                                            <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_ACCEPT_OFFER]}" />
                                        </form>
                                    </div>
                                </c:if>
                                <div class="order__actions">
                                    <!-- Open -->
                                    <form class="forms" method="post" action="<c:url value='controller?command=view_order' />">
                                        <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                        <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_OPEN_ORDER]}" />
                                    </form>
                                    <!-- Close -->
                                    <form class="forms" method="post" action="<c:url value='controller?command=close_order' />">
                                        <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                        <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_CLOSE_ORDER]}" />
                                    </form>
                                </div>
                            </c:when>
                            <c:when test="${order.status == 'FINISHED'}">
                                <c:if test="${order.bestOffer != null}" >
                                    <div class="offer">
                                        <span class="offer__header">${LOCALE[TEXT_OFFER_CARRIER]}</span>
                                        <span class="offer__carrier-name">${order.bestOffer.user.firstName} ${order.bestOffer.user.lastName}</span>
                                        <span class="offer__carrier-phone">${order.bestOffer.user.phone}</span>
                                        <span class="offer__carrier-price">${order.bestOffer.price} USD</span>
                                    </div>
                                </c:if>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <jsp:include page="${TEMPLATE_FORMS_ORDER}" />
</body>
</html>
