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

<% List<Product> productos = (List<Product>) request.getAttribute("products"); %>

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
                    <th width="300"></th>
                </tr>
                </thead>
                <tbody>
                <% for (Product producto : productos) { %>
                <tr>
                    <td><%=producto.getId() %>
                    </td>
                    <td><%=producto.getName() %>
                    </td>
                    <td><%=producto.getCategory().getName() %>
                    </td>
                    <td><%=producto.getPrice() %>
                    </td>
                    <td><img src="" alt=""></td>
                    <td class="text-right">
                        <a href="#" class="btn btn-info btn-sm"><i class="fa fa-eye"></i>
                            Mostrar</a>
                        <a href="#" class="btn btn-warning btn-sm"><i class="fa fa-edit"></i>
                            Editar</a>
                        <a
                                href="<%=request.getContextPath()%>/ProductoEliminarServlet?id=<%=producto.getId() %>"
                                class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> Eliminar</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <div class="card-footer">
            <a href="<%=request.getContextPath()%>/ProductoRegistrarServlet" class="btn btn-
success"><i class="fa fa-plus"></i> Nuevo</a>
        </div>
    </div>
</div>
</body>
</html>
