<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<p>
<tags:master/>
<a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>
<c:if test="${not empty cartItems}">
<p id="cartText" class="cart">${cart}</p>

  <form:form method ="post" modelAttribute="cartItemReducedDto">
  <input type="hidden" name="_method" value="put" />
  <table border="1px">
    <thead>
      <tr>
        <td>Image</td>
        <td>Brand
        <tags:sort sort = "brand" order = "asc"/>
        <tags:sort sort = "brand" order = "desc"/>
        </td>
        <td>Model
        <tags:sort sort = "model" order = "asc"/>
        <tags:sort sort = "model" order = "desc"/>
        </td>
        <td>Price
        <tags:sort sort = "price" order = "asc"/>
        <tags:sort sort = "price" order = "desc"/></td>
        <td>Colors</td>
        <td>Display size
        <tags:sort sort = "displaySizeInches" order = "asc"/>
          <tags:sort sort = "displaySizeInches" order = "desc"/>
        </td>
        <td>Quantity</td>
        <td>Action</td>
      </tr>
    </thead>
    <c:forEach var="cartItem" items="${cartItems}" varStatus="i">
      <tr>
        <td>
          <a href="${pageContext.servletContext.contextPath}/productDetails/${cartItem.phone.id}">
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.phone.imageUrl}">
          </a>
        </td>
        <td>${cartItem.phone.brand}</td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/productDetails/${cartItem.phone.id}">
            ${cartItem.phone.model}</td>
          </a>
        <td>
            <fmt:formatNumber value="${cartItem.phone.price}" type="currency" currencySymbol="$"/>
        </td>
        <td><c:forEach var="color" items="${cartItem.phone.colors}">${color.code} </c:forEach></td>
       <td>
            <fmt:formatNumber value="${cartItem.phone.displaySizeInches}" type="number" maxFractionDigits="1"/>''
       </td>
       <td>
            <form:hidden path="cartItemReduced[${i.index}].id" value="${cartItem.phone.id}"/>
            <form:input path="cartItemReduced[${i.index}].quantity" type="number"  value="${cartItem.quantity}"/>
            <p id="${cartItem.phone.id}_error" class="error"></p>
       </td>
       <td>
            <form method="post" id="deleteForm${cartItem.phone.id}">
            <input type="hidden" name="phoneId" value="${cartItem.phone.id}" />
             <button type="submit" onclick="onDeletePhone(${cartItem.phone.id},${i.index})">Delete</button>
            </form>
       </td>
       </form>
      </tr>
    </c:forEach>
  </table>
  <button name="update" type="submit">  Update </button>
  </form:form>
  <a href="${pageContext.servletContext.contextPath}/order"><button type="button" class="btn btn-info">Order</button></a>

</c:if>
<c:if test="${empty cartItems}">
<h2 class="emptyCart">Sorry, your cart is empty</h2>

</c:if>
<script>
function onDeletePhone(id) {
  const form = document.getElementById('deleteForm' + id);
  const input = document.createElement('input');
  input.setAttribute('type', 'hidden');
  input.setAttribute('name', 'phoneId');
  input.setAttribute('value', id);
  form.appendChild(input);
  form.submit();
}
</script>
</p>