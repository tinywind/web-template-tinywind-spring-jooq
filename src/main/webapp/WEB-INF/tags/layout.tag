<%@ tag pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%--@elvariable id="g" type="org.tinywind.webtemplate.config.RequestGlobal"--%>
<%--@elvariable id="cached" type="org.tinywind.webtemplate.config.CachedEntity"--%>
<%--@elvariable id="message" type="org.tinywind.webtemplate.config.RequestMessage"--%>

<tags:scripts/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>인사 고과평가</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="shortcut icon" type="image/ico" href="<c:url value="/resources/images/favicon.ico"/>"/>
    <tags:css/>
</head>
<body>

<jsp:doBody/>

<div id="scripts">
    <tags:js/>
    <tags:alerts/>
    <tags:scripts method="pop"/>
</div>

</body>
</html>