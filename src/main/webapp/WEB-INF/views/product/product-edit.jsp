<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Editar Producto</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Editar Producto</div>

    <div class="card">
        <div class="card-header">
            Editar Información del Producto
        </div>
        <div class="card-body">
            <form action="<c:url value='/products/edit'/>" method="post" enctype="multipart/form-data">
                <p>Debug - Product ID: ${product.id}</p>
                <input type="hidden" name="id" value="${product.id}"/>

                <div class="row">
                    <div class="col-md-6">
                        <c:if test="${not empty product.image_name}">
                            <div class="d-flex justify-content-center align-items-center mb-3">
                                <img src="<c:url value='/files/${product.image_name}'/>"
                                     alt="Imagen del Producto"
                                     style="max-width: 60%; height: auto"
                                     class="img-fluid rounded">
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="image">Actualizar Imagen:</label>
                            <input type="file" class="form-control" id="image" name="image"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="name">Nombre:</label>
                            <input type="text" class="form-control" id="name" name="name" value="${product.name}"
                                   required/>
                        </div>

                        <div class="form-group">
                            <label for="categoryId">Categoría:</label>
                            <select class="form-control" id="categoryId" name="categoryId" required>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="price">Precio:</label>
                            <input type="number" step="0.01" class="form-control" id="price" name="price"
                                   value="${product.price}" required/>
                        </div>

                        <div class="form-group">
                            <label for="stock">Stock:</label>
                            <input type="number" class="form-control" id="stock" name="stock" value="${product.stock}"
                                   required/>
                        </div>

                        <div class="form-group">
                            <label for="description">Descripción:</label>
                            <textarea class="form-control" id="description" name="description"
                                      rows="4">${product.description}</textarea>
                        </div>
                    </div>
                </div>

                <div class="form-group text-right">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Guardar Cambios</button>
                    <a href="<c:url value='/products'/>" class="btn btn-secondary"><i class="fa fa-chevron-left"></i>
                        Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>