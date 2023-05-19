<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
  <title>Order Not Found</title>
</head>
<body>
  <h1>Order Not Found</h1>
  <p>${error}</p>
  <p>Please check the URL and try again</p>
  <a href="${pageContext.servletContext.contextPath}/admin/orders">
    <p>
        Order list
    </p>
</a>
</body>