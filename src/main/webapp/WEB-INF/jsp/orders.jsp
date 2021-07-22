<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_ORDER_PAGE_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="${TEMPLATE_HEADER}" />
        <div class="orders">
            <c:forEach var="order" items="${RESP_ORDER_RESULT_LIST}" >
                <div class="flex flex__justify-content_space-between orders__item order order_${order.status}">
                    <div class="order-info">
                        <h2 class="order-info__route">${order.route}</h2>
                        <p class="order-info__details">${order.details}</p>
                    </div>
                    <div class="flex flex__align-items_flex-start">
                        <c:if test="${order.bestOffer != null}" >
                            <div class="best-price">
                                <span class="best-price__header">${LOCALE[TEXT_OFFER_BEST_OFFER_FROM]} ${order.offers.size()}</span>
                                <span class="best-price__carrier-name">${order.bestOffer.user.firstName} ${order.bestOffer.user.lastName}</span>
                                <span class="best-price__carrier-price">${order.bestOffer.price} USD</span>
                                <form class="forms" method="post" action="<c:url value='controller?command=' />">
                                    <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                    <input type="hidden" name="${KEY_OFFER_ID}" value="${order.bestOffer.id}">
                                    <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_ACCEPT_OFFER]}" />
                                </form>
                            </div>
                        </c:if>
                        <div class="order__actions">
                            <!-- Open -->
                            <form class="forms" method="post" action="<c:url value='controller?command=' />">
                                <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_OPEN_ORDER]}" />
                            </form>
                            <!-- Close -->
                            <form class="forms" method="post" action="<c:url value='controller?command=' />">
                                <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_CLOSE_ORDER]}" />
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
