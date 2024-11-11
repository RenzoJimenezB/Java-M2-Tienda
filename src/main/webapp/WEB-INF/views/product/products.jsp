<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="m2tienda.m2tienda.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tienda</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<%--<% String contextPath = request.getContextPath(); %>--%>

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
                            <c:if test="${product.image_name != null}">
                                <img src="${contextPath}/files/<c:out
                                     value="${product.image_name}"/>"
                                     alt=""
                                     height="30">
                            </c:if>
                        </td>

                        <td class="text-right">
                            <a href="${contextPath}/products/view?id=<c:out value="${product.id}"/>"
                               class="btn btn-info btn-sm"><i class="fa fa-eye"></i>
                                Mostrar</a>

                            <a href="${contextPath}/products/edit?id=<c:out value="${product.id}"/>"
                               class="btn btn-warning btn-sm"><i class="fa fa-edit"></i>
                                Editar</a>

                            <a href="${contextPath}/products/delete?id=<c:out value="${product.id}"/>"
                               class="btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
        <div class="card-footer">
            <a href="<%=request.getContextPath()%>/products/create" class="btn btn-success"><i class="fa fa-plus"></i>
                Nuevo
            </a>
        </div>
    </div>
</div>
</body>
</html>
