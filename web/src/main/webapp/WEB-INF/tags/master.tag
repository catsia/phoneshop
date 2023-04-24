<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>

 <script src="${pageContext.request.contextPath}/resources/js/cartAjax.js">
 </script>


<form>
    <input name = "query" value = "${param.query}">
    <button>Search</button>
  </form>

 <link href="${pageContext.request.contextPath}/resources/styles/main.css" rel="stylesheet"/>
</head>
