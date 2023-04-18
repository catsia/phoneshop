<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href = "?page=${currentPage}&sort=${sort}&order=${order}&query=${param.query} ">
<c:choose>
  <c:when test="${sort eq param.sort and order eq param.order}">
  <c:if test="${order eq 'asc'}">
           &#8593
   </c:if>
  <c:if test="${order eq 'desc'}">
            &#8595
   </c:if>
  </c:when>

  <c:otherwise>
 <c:if test="${order eq 'asc'}">
          &#8593
  </c:if>
  <c:if test="${order eq 'desc'}">
        &#8595
  </c:if>
  </c:otherwise>
</c:choose>

</a>
