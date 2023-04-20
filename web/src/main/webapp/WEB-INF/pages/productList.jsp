<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<p>
<head>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
<script
 <script type = "text/javascript">
    $(document).ready(function(){
         addToCartAjax = function(phoneId){
        var quantity = $('#'+phoneId+'_quantity').val();
        $.ajax({
            type: "POST",
            url:"ajaxCart",
           data: {
                phoneId: phoneId,
                quantity: quantity
           },
        }).done(function(data) {
            console.log(quantity);
            console.log(phoneId);
            $('#successes').text(data);
            $('#error').text("");
            alert("Success.");
        }).fail(function() {
            $('#error').text("Error while adding to the cart");
            $('#'+phoneId+'_error').text("Quantity can't be zero or negative");

            $('#successes').text("");
            //console.log(quantity);
            //console.log(phoneId);
            alert("Sorry. Server unavailable. ");
        });
        }
    });
 </script>


 <link href="${pageContext.request.contextPath}/resources/styles/main.css" rel="stylesheet"/>
 </head>
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
        <td>$ ${phone.price}</td>
        <td><c:forEach var="color" items="${phone.colors}">${color.code} </c:forEach></td>
       <td>${phone.displaySizeInches}</td>
       <form>
       <td>
            <input type="hidden" value=${phone.id} id="phoneId" class="phoneId"/>
            <input type="number" id="${phone.id}_quantity" class="quantity" value="1"/>
            <p id="${phone.id}_error" class="error"></p>
       </td>
       <td>
            <input type="button" value="Add to cart"  onclick="addToCartAjax(${phone.id})" id="addToCart" class="addToCart"/>
       </td>
       </form>
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