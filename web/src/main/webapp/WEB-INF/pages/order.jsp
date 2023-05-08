<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<p>
<tags:master/>
<a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>

<c:if test="${not empty errors}">
    <span class="error">Error while updating the cart</span>
</c:if>
<c:if test="${empty errors && not empty successes}">
    <span id="successes">${successes}</span>
</c:if>

  <table border="1px">
    <thead>
      <tr>
        <td>Image</td>
        <td>Brand
        </td>
        <td>Model
        </td>
        <td>Display size
        <td>Colors</td>
        <td>Quantity
        </td>
        <td>Price</td>
      </tr>
    </thead>
    <c:forEach var="orderItem" items="${order.orderItems}">
      <tr>
        <td>
          <a href="${pageContext.servletContext.contextPath}/productDetails/${orderItem.phone.id}">
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}">
          </a>
        </td>
        <td>${orderItem.phone.brand}</td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/productDetails/${orderItem.phone.id}">
            ${orderItem.phone.model}</td>
          </a>
        <td>
              <fmt:formatNumber value="${orderItem.phone.displaySizeInches}" type="number" maxFractionDigits="1"/>''

        </td>
        <td><c:forEach var="color" items="${orderItem.phone.colors}">${color.code} </c:forEach></td>
       <td>
                        ${orderItem.quantity}

       </td>
       <td>
            <fmt:formatNumber value="${orderItem.phone.price}" type="currency" currencySymbol="$"/>

       </td>

       <tr>

      </tr>

    </c:forEach>
    <tr>
         <td></td>
         <td></td>
         <td></td>
        <td></td>
        <td></td>
        <td></td>
         <td>
                 Subtotal:
                 <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/>
             </td>
         </tr>
         <tr>
              <td></td>
              <td></td>
              <td></td>
             <td></td>
             <td></td>
             <td></td>
              <td>
                      Delivery price:
                       <fmt:formatNumber value="${order.deliveryPrice}" type="currency" currencySymbol="$"/>
                  </td>
              </tr>
     <tr>
     <td></td>
     <td></td>
     <td></td>
    <td></td>
    <td></td>
    <td></td>
     <td>
             TOTAL:
             <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/>
         </td>
     </tr>
  </table>
  <p class="error">${orderItemsError}</p></td>
   <form:form method="POST" modelAttribute="orderReduced">

    <table>
            <c:forEach var="orderItem" items="${order.orderItems}" varStatus="i">
                <form:hidden path="orderItems[${orderItem.phone.id}]" value="${orderItem.quantity}" />
            </c:forEach>

        <tr>
            <td><form:label path="firstName">First name*:</form:label></td>
            <td><form:input path="firstName" />
                <p class="error">${firstNameError}</p>
            </td>
        </tr>
        <tr>
            <td><form:label path="lastName">Last name*:</form:label></td>
            <td><form:input path="lastName"  />
                <p class="error">${lastNameError}</p></td>
        </tr>
        <tr>
            <td><form:label path="deliveryAddress">Address*:</form:label></td>
            <td><form:input path="deliveryAddress"     />
                <p class="error">${deliveryAddressError}</p></td>
        </tr>
        <tr>
            <td><form:label path="contactPhoneNo">Phone*:</form:label></td>
            <td><form:input path="contactPhoneNo"     />
                <p class="error">${contactPhoneNoError}</p></td>
        </tr>
        <tr>
                    <td><form:label path="additionalInformation">Additional information:</form:label></td>

            <td colspan="2"><form:textarea path="additionalInformation"     />
                <td><p class="error">${additionalInformationError}</p></td>
        </tr>
        <tr>
            <td></td>
            <td><button name="order">Order</button></td>
        </tr>
    </table>
</form:form>
</p>