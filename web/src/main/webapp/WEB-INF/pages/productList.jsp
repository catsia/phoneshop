<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<p>
  Hello from product list!
</p>
<p>
  Found <c:out value="${phones.size()}"/> phones.
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
      </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        </td>
        <td>${phone.brand}</td>
        <td>${phone.model}</td>
        <td>$ ${phone.price}</td>
        <td><c:forEach var="color" items="${phone.colors}">${color.code} </c:forEach></td>
       <td>${phone.displaySizeInches}</td>
      </tr>
    </c:forEach>
  </table>
    <div class="paginationSection">
            <a href="${pageContext.servletContext.contextPath}/productList?page=${previousPage}" >Previous</a>

            <tags:pagination pageNumber="1"/>
            <tags:pagination pageNumber="2"/>
            <tags:pagination pageNumber="3"/>
            <tags:pagination pageNumber="4"/>
            <tags:pagination pageNumber="5"/>
            <tags:pagination pageNumber="6"/>
            <tags:pagination pageNumber="7"/>
            <tags:pagination pageNumber="8"/>
            <tags:pagination pageNumber="9"/>

            <a href="${pageContext.servletContext.contextPath}/productList?page=${nextPage}" >Next</a>

    </div>
</p>