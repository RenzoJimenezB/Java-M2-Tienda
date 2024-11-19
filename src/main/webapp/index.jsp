<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to the Store</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col-12 text-center">
            <h1 class="display-4">Welcome to Our Store!</h1>
            <p class="lead">Explore our products and make your purchases today.</p>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-6 d-flex justify-content-center">
            <a href="<c:url value='/hello-servlet'/>" class="btn btn-primary btn-lg">
                <i class="fa fa-hand-wave"></i> Hello Servlet
            </a>
        </div>
        <div class="col-md-6 d-flex justify-content-center">
            <a href="<c:url value='/catalog'/>" class="btn btn-success btn-lg">
                <i class="fa fa-shopping-cart"></i> View Product Catalog
            </a>
        </div>
    </div>
</div>

</body>
</html>