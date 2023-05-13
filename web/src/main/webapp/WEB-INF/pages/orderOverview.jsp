<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<p>
<tags:master/>
<h2>Order number: ${order.id}</h2>

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
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}">
        </td>
        <td>${orderItem.phone.brand}</td>
        <td>
            ${orderItem.phone.model}</td>
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


    <table>

        <tr>
            <td>${order.firstName} ${order.lastName}
            </td>
        </tr>
        <tr>
            <td>${order.deliveryAddress}</td>
        </tr>
        <tr>
            <td>${order.contactPhoneNo}</td>
        </tr>
        <tr>
            <c:if test="${not empty order.additionalInformation}">
                        <td>${order.additionalInformation}</td>
            </c:if>
        </tr>

    </table>


<a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>

</p>