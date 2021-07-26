<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_OFFER_PAGE_TITLE]}" />
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
            <div class="forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
        </c:if>
        <c:forEach var="order" items="${RESP_ORDER_RESULT_LIST}" >
            <div class="flex flex__justify-content_space-between orders__item order order_${order.status}">
                <div class="order-info">
                    <c:if test="${order.status == 'FINISHED'}">
                        <p class="order-info__shipper">${order.user.firstName} ${order.user.lastName} (${order.user.phone})</p>
                    </c:if>
                    <h2 class="order-info__route">${order.route}</h2>
                    <pre class="order-info__details">${order.details}</pre>
                </div>
                <div class="flex flex__align-items_flex-start">
                    <c:choose>
                        <c:when test="${order.status == 'NEW'}">
                            <c:choose>
                                <c:when test="${order.bestOffer != null}">
                                    <div class="offer">
                                        <span class="offer__header">${LOCALE[TEXT_OFFER_PRICE]}</span>
                                        <span class="offer__carrier-price">${order.bestOffer.price} USD</span>
                                        <form class="forms" method="post" action="<c:url value='controller?command=cancel_offer' />">
                                            <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                            <input type="hidden" name="${KEY_OFFER_ID}" value="${order.bestOffer.id}">
                                            <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_CANCEL_OFFER]}" />
                                        </form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="offer">
                                        <form class="forms" method="post" action="<c:url value='controller?command=make_offer' />">
                                            <label class="forms__element" for="offer_price">${LOCALE[TEXT_OFFER_PRICE]} (USD):</label>
                                            <input class="forms__element forms__input" id="offer_price" name="${KEY_OFFER_PRICE}" placeholder="10000" pattern="${VALIDATION_PRICE_PATTERN}" data-invalid-text="${LOCALE[TEXT_FORM_ERROR_PRICE_TEXT]}" required>
                                            <input type="hidden" name="${KEY_ORDER_ID}" value="${order.id}">
                                            <input class="forms__element" type="submit" value="${LOCALE[TEXT_OFFER_BUTTON_MAKE_OFFER]}" />
                                        </form>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${order.status == 'FINISHED'}">
                            <c:if test="${order.bestOffer != null}" >
                                <div class="offer">
                                    <span class="offer__header">${LOCALE[TEXT_OFFER_CARRIER]}</span>
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
</body>
</html>