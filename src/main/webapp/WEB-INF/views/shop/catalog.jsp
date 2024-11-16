<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Catalog</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Catálogo de Productos</div>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success"/>
    </c:if>

    <div class="row">

        <c:forEach items="${products}" var="product">
            <div class="col-sm-4 col-md-3">
                <div class="card shadow mb-3">
                    <div class="card-header">
                            ${product.category.name}
                    </div>

                    <c:if test="${not empty product.image_name}">
                        <div class="d-flex justify-content-center align-items-center">
                            <img src="<c:url value="files/${product.image_name}"/>"
                                 alt=""
                                 style="max-width: 80%; height: auto;">
                        </div>
                    </c:if>

                    <div class="card-body">
                        <p>${product.name}</p>
                        <p><b>S/${product.price}</b></p>
                    </div>

                    <div class="card-footer">
                        <a href="
                            <c:url value="/purchase"><c:param name="id" value="${product.id}"/>
                            </c:url>" class="btn btn-primary"><i class="fa fa-shopping-cart"></i>
                            Comprar
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
</div>
</body>
</html>
