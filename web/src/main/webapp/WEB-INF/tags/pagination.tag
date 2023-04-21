<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageNumber" required="true" %>

<html>
        <a href="${pageContext.servletContext.contextPath}/productList?page=${pageNumber}&sort=${param.sort}&order=${param.order}&query=${param.query}" >${pageNumber}</a>
</html>