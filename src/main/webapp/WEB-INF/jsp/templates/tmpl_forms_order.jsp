<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="modal-new-order" class="modal-window modal-window_hidden">
    <div class="modal-window__form">
        <span class="modal-window__close">X</span>
        <form method="post" action="<c:url value='controller?command=create_order' />" class="forms">
            <h2 class="forms__element forms__header">${LOCALE[TEXT_ORDER_NEW_ORDER_HEADER]}</h2>
            <label class="forms__element" for="order_route">${LOCALE[TEXT_ORDER_NEW_ORDER_ROUTE]}:</label>
            <input class="forms__element" id="order_route" name="${KEY_ORDER_ROUTE}" type="text" pattern="${VALIDATION_TEXT_PATTERN}" required/>

            <label class="forms__element" for="order_details">${LOCALE[TEXT_ORDER_NEW_ORDER_DETAILS]}:</label>
            <textarea class="forms__element" id="order_details" name="${KEY_ORDER_DETAILS}" rows="10" required></textarea>

            <input class="forms__element" type="submit" value="${LOCALE[TEXT_ORDER_BUTTON_CREATE]}" />
        </form>
    </div>
</div>