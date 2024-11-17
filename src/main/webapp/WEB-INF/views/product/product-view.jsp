<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Detalle del Producto</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Detalle del Producto</div>

    <div class="card">
        <div class="card-header">
            Información del Producto
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <c:if test="${not empty product.image_name}">
                        <div class="d-flex justify-content-center align-items-center">
                            <img src="<c:url value="/files/${product.image_name}"/>"
                                 alt="Imagen del Producto"
                                 style="max-width: 60%; height: auto"
                                 class=" img-fluid rounded">
                        </div>
                    </c:if>
                </div>
                <div class="col-md-6">
                    <h5>Nombre:</h5>
                    <p>${product.name}</p>

                    <h5>Categoría:</h5>
                    <p>${product.category.name}</p>

                    <h5>Precio:</h5>
                    <p>S/${product.price}</p>

                    <h5>Stock:</h5>
                    <p>${product.stock}</p>

                    <h5>Descripción:</h5>
                    <p>${product.description}</p>
                </div>
            </div>
        </div>
        <div class="card-footer text-right">
            <a href="<c:url value="/products"/>" class="btn btn-secondary"><i class="fa fa-chevron-left"></i> Volver a
                la lista</a>
        </div>
    </div>
</div>
</body>
</html>