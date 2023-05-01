<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<p>
<tags:master/>
<span id="successes"></span>
<span id="error" class="error"></span>
<a href="${pageContext.servletContext.contextPath}/cart">
<p id="cartText" class="cart">${cart}</p>
</a>
<a href="${pageContext.servletContext.contextPath}/"><button type="button" class="btn btn-info">Back to product list</button></a>
 <body>
     <div class="container">
       <div class="row">
         <div>
           <h2>${phone.model}</h2>
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
           <p>${phone.description}</p>
         </div>
        <form>
        <h2>
        Price: <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/>
        </h2>
        <input type="number" id="${phone.id}_quantity" class="quantity" value="1"/>
        <p id="${phone.id}_error" class="error"></p>
        <input type="button" value="Add to cart"  onclick="CartAjax(${phone.id})" id="addToCart" class="addToCart"/>
        </form>

         <div class="col-sm-6">
           <h2>Display</h2>
           <table class="details-table">
             <tr>
               <td>Size</td>
               <td><fmt:formatNumber value="${phone.displaySizeInches}" type="number" maxFractionDigits="1"/>''</td>
             </tr>
             <tr>
               <td>Resolution</td>
               <td>${phone.displayResolution}</td>
             </tr>
             <tr>
               <td>Technology</td>
               <td>${phone.displayTechnology}</td>
             </tr>
             <tr>
               <td>Pixel density</td>
               <td>${phone.pixelDensity}</td>
             </tr>

           </table>
         </div>

         <div class="col-sm-6">
            <h2>Dimensions & weight</h2>
            <table class="details-table">
              <tr>
                <td>Length</td>
                <td>${phone.lengthMm}mm</td>
              </tr>
              <tr>
                <td>Width</td>
                <td>${phone.widthMm}mm</td>
              </tr>

              <tr>
                <td>Weight</td>
                <td>${phone.weightGr}</td>
              </tr>

            </table>
          </div>

          <div class="col-sm-6">
              <h2>Camera</h2>
              <table class="details-table">
                <tr>
                  <td>Front</td>
                  <td><fmt:formatNumber value="${phone.frontCameraMegapixels}" type="number" maxFractionDigits="1"/> megapixels</td>
                </tr>
                <tr>
                  <td>Back</td>
                  <td><fmt:formatNumber value="${phone.backCameraMegapixels}" type="number" maxFractionDigits="1"/> megapixels</td>
                </tr>
              </table>
            </div>

        <div class="col-sm-6">
            <h2>Battery</h2>
            <table class="details-table">
              <tr>
                <td>Talk time</td>
                <td><fmt:formatNumber value="${phone.talkTimeHours}" type="number" maxFractionDigits="1"/> hours</td>
              </tr>
              <tr>
                <td>Stand by time</td>
                <td><fmt:formatNumber value="${phone.standByTimeHours}" type="number" maxFractionDigits="1"/> hours</td>
              </tr>
              <tr>
                <td>Battery capacity</td>
                <td>${phone.batteryCapacityMah}mAh</td>
              </tr>
            </table>
          </div>

        <div class="col-sm-6">
            <h2>Other</h2>
            <table class="details-table">
              <tr>
                  <td>Color</td>
                  <td><c:forEach var="color" items="${phone.colors}">${color.code} </c:forEach></td>
                </tr>
              <tr>
                <td>Device type</td>
                <td>${phone.deviceType}</td>
              </tr>
              <tr>
                <td>Bluetooth</td>
                <td>${phone.bluetooth}</td>
              </tr>
            </table>
          </div>
       </div>
     </div>
   </body>

</p>