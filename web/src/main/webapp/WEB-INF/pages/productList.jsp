<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<p>
<tags:master/>
<span id="successes"></span>
<span id="error" class="error"></span>
<p id="cartText" class="cart">${cart}</p>

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
    <c:forEach var="phone" items="${phones}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        </td>
        <td>${phone.brand}</td>
        <td>${phone.model}</td>
        <td>
            <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/>
        </td>
        <td><c:forEach var="color" items="${phone.colors}">${color.code} </c:forEach></td>
       <td>
            <fmt:formatNumber value="${phone.displaySizeInches}" type="number" maxFractionDigits="1"/>''
       </td>
       <form>
       <td>
            <input type="hidden" value=${phone.id} id="phoneId" class="phoneId"/>
            <input type="number" id="${phone.id}_quantity" class="quantity" value="1"/>
            <p id="${phone.id}_error" class="error"></p>
       </td>
       <td>
            <input type="button" value="Add to cart"  onclick="CartAjax(${phone.id})" id="addToCart" class="addToCart"/>
       </td>
       </form>
      </tr>
    </c:forEach>
  </table>
    <div class="paginationSection">
            <a href="${pageContext.servletContext.contextPath}/productList?page=${previousPage}&sort=${sort}&order=${order}&query=${param.query}" >Previous</a>

            <tags:pagination pageNumber="1" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="2" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="3" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="4" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="5" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="6" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="7" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="8" currentPage="${currentPage}"/>
            <tags:pagination pageNumber="9" currentPage="${currentPage}"/>

            <a href="${pageContext.servletContext.contextPath}/productList?page=${nextPage}&sort=${sort}&order=${order}&query=${param.query}" >Next</a>

    </div>
</p>