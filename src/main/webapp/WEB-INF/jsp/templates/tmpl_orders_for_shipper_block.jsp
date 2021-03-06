<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:forEach var="order" items="${RESP_ORDER_RESULT_LIST}" >
    <div class="flex flex__justify-content_space-between orders__item order order_${order.status}">
        <div class="order-info">
            <h2 class="order-info__route flex flex__align-items_center">
                <c:choose>
                    <c:when test="${order.status == 'NEW'}">
                        <span class="label label_new">${LOCALE[TEXT_ORDER_LABEL_NEW]}</span>
                    </c:when>
                    <c:when test="${order.status == 'FINISHED'}">
                        <span class="label label_finished">${LOCALE[TEXT_ORDER_LABEL_FINISHED]}</span>
                    </c:when>
                    <c:when test="${order.status == 'CLOSED'}">
                        <span class="label label_closed">${LOCALE[TEXT_ORDER_LABEL_CLOSED]}</span>
                    </c:when>
                </c:choose>
                ${order.route}
            </h2>
            <pre class="order-info__details">${order.details}</pre>
        </div>
        <div class="flex flex__align-items_flex-start">
            <c:choose>
                <c:when test="${order.status == 'NEW'}">
                    <c:if test="${order.bestOffer != null}" >
                        <div class="offer">
                            <span class="offer__header">${LOCALE[TEXT_OFFER_BEST_OFFER_FROM]} ${order.offers.size()}</span>
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