<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<p>
<tags:master/>


  <table border="1px">
    <thead>
      <tr>
        <td>Order number</td>
        <td>Customer
        </td>
        <td>Phone
        </td>
        <td>Address
        <td>Date</td>
        <td>Total price
        </td>
        <td>Status</td>
      </tr>
    </thead>
      <c:forEach var="order" items="${orders}">
      <tr>
        <td>
          <a href="${pageContext.servletContext.contextPath}/admin/orders/${order.id}">
          ${order.id}
          </a>
        </td>
        <td>${order.firstName} ${order.lastName}</td>
        <td>
            ${order.contactPhoneNo}</td>
        <td>
              ${order.deliveryAddress}
        </td>
        <td>${order.date}</td>
       <td>
                ${order.totalPrice}
       </td>
       <td>
            ${order.status}
       </td>
       <tr>
      </tr>
    </c:forEach>
    </table>

<a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>

</p>