<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageNumber" required="true" %>

<html>
        <a href="${pageContext.servletContext.contextPath}/productList?page=${pageNumber}" >${pageNumber}</a>
</html>