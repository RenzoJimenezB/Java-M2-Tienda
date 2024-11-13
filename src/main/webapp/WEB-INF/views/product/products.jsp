<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tienda</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Mantenimiento de Productos</div>
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
                    <th width="300">ACCIONES</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${products}" var="product">
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
                            <a href="${contextPath}/products/view?id=<c:out value="${product.id}"/>"
                               class="btn btn-info btn-sm"><i class="fa fa-eye"></i>
                                Mostrar</a>

                            <a href="
                                <c:url value="/products/edit"><c:param name="id" value="${product.id}"/>
                                </c:url>" class=" btn btn-warning btn-sm"><i class="fa fa-edit"></i>
                                Editar</a>

                            <a href="
                                <c:url value="/products/delete"><c:param name="id" value="${product.id}"/>
                                </c:url> " class="btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
        <div class="card-footer">
            <a href="<c:url value="/products/create"/>" class="btn btn-success"><i class="fa fa-plus"></i>
                Nuevo
            </a>
        </div>
    </div>
</div>
</body>
</html>
