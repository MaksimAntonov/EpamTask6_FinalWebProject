<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:redirect url="controller?command=GO_TO_LOGIN_REGISTRATION_PAGE" />
<!DOCTYPE html>
<html>
    <head>
        <title>JSP - Hello World</title>
        <jsp:include page="WEB-INF/jsp/templates/tmpl_head_data.jsp" />
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="${TEMPLATE_HEADER}" />
            ${REQUIRED_URI}
            <div class="user-login-and-registration">
                <form method="post" action="controller?command=login_user" class="user-login-and-registration__item forms">
                    <label for="login_email"></label>
                    <input id="login_email" name="email" type="email"/>
                    <label for="login_pass">Password</label>
                    <input id="login_pass" name="password" type="password"/>
                    <hr />
                    <input type="submit" value="LogIn">
                </form>
                <form method="post" action="controller?command=registration" class="user-login-and-registration__item forms">
                    <label for="user_email">Email</label>
                    <input id="user_email" name="email" type="email"/>
                    <label for="user_pass">Password</label>
                    <input id="user_pass" name="password" type="password"/>
                    <input type="submit" value="Create User">
                </form>
            </div>
        </div>

        <hr />
        <a href="controller?command=open_orders">Orders</a>
    </body>
</html>