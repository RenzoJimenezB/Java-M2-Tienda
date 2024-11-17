<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Checkout</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Productos en Carrito</div>

    <c:if test="${not empty sessionScope.success}">
    <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success"/>
    </c:if>

    <div class="card">
        <div class="card-header">
            Lista de Productos
        </div>
        <div class="card-body">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <th>CATEGORÍA</th>
                    <th>PRECIO</th>
                    <th>IMAGEN</th>
                    <th width="300"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${shoppingCart}" var="product">
                    <tr>
                        <td><c:out value="${product.id}"/></td>
                        <td><c:out value="${product.name}"/></td>
                        <td><c:out value="${product.category.name}"/></td>
                        <td><c:out value="${product.price}"/></td>
                        <td>
                            <c:if test="${not empty product.image_name}">
                                <img src="<c:url value="files/${product.image_name}"/>"
                                     alt=""
                                     height="30">
                            </c:if>
                        </td>
                        <td class="text-right">
                            <a href="#" class="btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                Retirar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="card-footer">
            <a href="#" class="btn btn-success"><i class="fa fa-shopping-cart"></i> Pagar</a>
            <a href="<c:url value="/catalog"/>" class="btn btn-secondary"><i
                    class="fa fa-chevron-left"></i> Seguir Comprando></a>
        </div>
    </div>

</body>
</html>
