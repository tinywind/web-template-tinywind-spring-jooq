<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%--@elvariable id="g" type="org.tinywind.webtemplate.config.RequestGlobal"--%>
<%--@elvariable id="cached" type="org.tinywind.webtemplate.config.CachedEntity"--%>

<%--@elvariable id="form" type="org.tinywind.webtemplate.model.form.LoginRequest"--%>

<tags:scripts/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>인사 고과평가</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="shortcut icon" type="image/ico" href="<c:url value="/resources/images/favicon.ico"/>"/>
</head>
<body>

<form:form id="form" commandName="form" method="post"
           action="${pageContext.request.contextPath}/api/user/login"
           cssClass="loginBox" data-ajax="true" data-done="successLogin">
    <ol>
        <li>
            <div><form:input path="id" placeholder="아이디"/></div>
        </li>
        <li>
            <div><form:password path="password" placeholder="비밀번호"/></div>
        </li>
        <li class="chkBoxWrap">
            <input type="checkbox" id="remember">
            <label for="remember">내 정보 기억하기</label>
        </li>
        <li>
            <button type="button" onclick="login()" class="loginBtn">LOGIN</button>
        </li>
    </ol>
</form:form>

<tags:scripts>
    <script type="text/javascript">
        const STORAGE_KEY = 'loginForm';
        const form = $('#form');
        const remember = $('#remember');

        function login() {
            restSelf.get("/api/user/login", form.formDataObject()).done(successLogin);
        }

        function successLogin() {
            if (localStorage)
                localStorage.setItem(STORAGE_KEY, remember.prop('checked') ? JSON.stringify(form.formDataObject()) : null);

            location.href = "${pageContext.request.contextPath}/main";
        }

        $(document).ready(function () {
            if (!localStorage) return;

            const storedValues = localStorage.getItem(STORAGE_KEY);
            if (!storedValues) return;

            remember.prop('checked', true);
            const values = JSON.parse(storedValues);
            const inputs = form.find('[name]');
            for (let key in values) {
                if (values.hasOwnProperty(key)) {
                    inputs.filter(function () {
                        return $(this).attr('name') === key;
                    }).val(values[key]);
                }
            }
        });
    </script>
</tags:scripts>

<div id="scripts">
    <tags:js/>
    <tags:alerts/>
    <tags:scripts method="pop"/>
</div>

</body>
</html>