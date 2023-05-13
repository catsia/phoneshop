<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
  <title>Order Is Empty</title>
</head>
<body>
  <h1>Order Is Empty</h1>
  <p>${error}</p>
  <p>Please add something to your cart to order</p>
  <a href="${pageContext.servletContext.contextPath}/"><button type="button">Back to product list</button></a>

</body>