<%@ tag pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%--@elvariable id="g" type="org.tinywind.webtemplate.config.RequestGlobal"--%>
<%--@elvariable id="cached" type="org.tinywind.webtemplate.config.CachedEntity"--%>
<%--@elvariable id="message" type="org.tinywind.webtemplate.config.RequestMessage"--%>

<%--@elvariable id="devel" type="java.lang.Boolean"--%>
<%--@elvariable id="version" type="java.lang.String"--%>

<!-- external library common -->
<script src="<c:url value="/webjars/jquery/3.3.1/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/jquery-blockui/2.65/jquery.blockUI.js"/>"></script>
<script src="<c:url value="/webjars/momentjs/2.21.0/min/moment.min.js"/>"></script>
<script src="<c:url value="/webjars/toastr/2.1.2/toastr.js"/>"></script>

<!-- external library depend -->
<script src="<c:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"/>"></script>

<%-- user library --%>
<script src="<c:url value="/resources/js/string.ex.js?version=${version}"/>" data-type="library"></script>
<script src="<c:url value="/resources/js/formData.ex.js?version=${version}"/>" data-type="library"></script>
<script src="<c:url value="/resources/js/jquery.ex.js?version=${version}"/>" data-type="library"></script>
<script src="<c:url value="/resources/js/jquery.leanModal.js?version=${version}"/>" data-type="library"></script>
<script src="<c:url value="/resources/js/jquery.bind.helpers.js?version=${version}"/>" data-type="library"></script>

<%-- functions --%>
<script src="<c:url value="/resources/js/common.func.js?version=${version}"/>" data-type="library"></script>
<script src="<c:url value="/resources/js/depend.func.js?version=${version}"/>" data-type="library"></script>

<%-- use strict --%>
<script src="<c:url value="/resources/js/depend.use.strict.js?version=${version}"/>" data-type="library"></script>

<%-- external --%>

<script>
    window.disableLog = ${devel};
    window.contextPath = '${pageContext.request.contextPath}';
    window.loadingImageSource = contextPath + '/resources/images/loading.gif';
    <c:if test="${g.login}">
    window.userId = '${g.escapeQuote(g.user.id)}';
    </c:if>
</script>