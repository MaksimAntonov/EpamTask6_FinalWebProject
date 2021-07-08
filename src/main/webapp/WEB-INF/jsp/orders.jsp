<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
    <jsp:include page="templates/tmpl_head_data.jsp" />
</head>
<body>
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
