<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Orders</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
    <jsp:include page="${TEMPLATE_HEADER}" />
    <div class="wrapper">
        <table>
            <c:forEach var="order" items="${orderList}" >
                <tr>
                    <td>${order.id}</td>
                    <td>${order.details}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
