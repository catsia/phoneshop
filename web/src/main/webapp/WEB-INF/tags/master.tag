<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>

 <script src="${pageContext.request.contextPath}/resources/js/cartAjax.js">
 </script>
  <script src="${pageContext.request.contextPath}/resources/js/onDeletePhone.js">
  </script>

 <link href="${pageContext.request.contextPath}/resources/styles/main.css" rel="stylesheet"/>
</head>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="authentication">
    admin
    <a href="${pageContext.servletContext.contextPath}/admin/orders">Admin</a>
    <a href="${pageContext.servletContext.contextPath}/logout">Logout</a>
    </div>
</sec:authorize>
<sec:authorize access="hasRole('ANONYMOUS')">
    <div class="authentication">
    <a href="${pageContext.servletContext.contextPath}/login">Login</a>
    </div>
</sec:authorize>