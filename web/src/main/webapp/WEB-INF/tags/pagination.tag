<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="currentPage" required="true" %>

<html>
       <c:if test="${currentPage > 9}">
           <c:set var = "page" value = "${currentPage+pageNumber-1}"/>
       </c:if>
       <c:if test="${currentPage <= 9}">
            <c:set var = "page" value = "${pageNumber}"/>
       </c:if>
       <c:if test="${page eq currentPage}">
           <b>
           <a href="${pageContext.servletContext.contextPath}/productList?page=${page}&sort=${param.sort}&order=${param.order}&query=${param.query}" >${page}</a>
           </b>
       </c:if>
       <c:if test="${page ne currentPage}">
           <a href="${pageContext.servletContext.contextPath}/productList?page=${page}&sort=${param.sort}&order=${param.order}&query=${param.query}" >${page}</a>
       </c:if>
</html>