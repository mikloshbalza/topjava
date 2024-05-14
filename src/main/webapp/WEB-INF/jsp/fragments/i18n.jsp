<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
const i18n = {}; // https://learn.javascript.ru/object
i18n["addTitle"] = '<spring:message code="user.add"/>';
i18n["editTitle"] = '<spring:message code="user.edit"/>';

<c:forEach var="key" items='${["common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
</c:forEach>
</script>