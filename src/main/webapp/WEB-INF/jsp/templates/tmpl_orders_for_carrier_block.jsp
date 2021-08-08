<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="orders">
    <c:forEach var="order" items="${RESP_ORDER_RESULT_LIST}" >
        <div class="flex flex__justify-content_space-between orders__item order order_${order.status}">
            <div class="order-info">
                <c:if test="${order.status == 'FINISHED'}">
                    <p class="order-info__shipper">${order.user.firstName} ${order.user.lastName} (${order.user.phone})</p>
                </c:if>
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
                        <c:choose>
                            <c:when test="${order.bestOffer != null}">
                                <div class="offer">
                                    <span class="offer__header">${LOCALE[TEXT_OFFER_OFFERED_PRICE]}</span>
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
                                        <label class="forms__element">${LOCALE[TEXT_OFFER_PRICE]} (USD):
                                            <input class="forms__element forms__input" id="offer_${order.id}" name="${KEY_OFFER_PRICE}" placeholder="10000" pattern="${VALIDATION_PRICE_PATTERN}" data-invalid-text="${LOCALE[TEXT_FORM_ERROR_PRICE_TEXT]}" required>
                                        </label>
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
                                <span class="offer__header">${LOCALE[TEXT_OFFER_ACCEPTED_PRICE]}</span>
                                <span class="offer__carrier-price">${order.bestOffer.price} USD</span>
                            </div>
                        </c:if>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </c:forEach>
</div>