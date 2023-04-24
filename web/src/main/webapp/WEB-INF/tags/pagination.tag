<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="currentPage" required="true" %>

<html>
       <c:if test="${pageNumber eq currentPage}">
           <b>
           <a href="${pageContext.servletContext.contextPath}/productList?page=${pageNumber}&sort=${param.sort}&order=${param.order}&query=${param.query}" >${pageNumber}</a>
           </b>
       </c:if>
       <c:if test="${pageNumber ne currentPage}">
           <a href="${pageContext.servletContext.contextPath}/productList?page=${pageNumber}&sort=${param.sort}&order=${param.order}&query=${param.query}" >${pageNumber}</a>
       </c:if>
</html>