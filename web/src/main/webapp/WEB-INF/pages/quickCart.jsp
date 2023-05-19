<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<p>
<tags:master/>
<a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>
<p id="cartText" class="cart">My cart: ${cart.totalQuantity} items $ ${cart.totalCost}</p>
<c:if test="${not empty errors}">
    <span class="error">There were errors</span>
</c:if>
<c:if test="${empty errors && not empty successes}">
    <span id="successes">${successes}</span>
</c:if>
  <form:form method="post" modelAttribute="quickCart">
  <table border="1px">
    <thead>
      <tr>
        <td>Phone name</td>
        <td>Quantity</td>
      </tr>
    </thead>
    <c:forEach begin="1" end="10" varStatus="i">
      <tr>
        <td>
            <form:input path="cartItems[${i.index}].phoneName" value="${empty errors ? _ : quickCartAfterErrors.cartItems[i.index].phoneName}"/>
        </td>
        <td>
            <form:input path="cartItems[${i.index}].quantity" value="${empty errors ? _ : quickCartAfterErrors.cartItems[i.index].quantity}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
  <button type="submit">Add to cart</button>
</form:form>
</p>