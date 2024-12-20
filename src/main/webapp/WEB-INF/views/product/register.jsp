<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Product</title>
    <%@include file="/WEB-INF/includes/head.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<div class="container-fluid pt-3">
    <div class="display-4 mb-3">Mantenimiento de Productos</div>

    <form action="<c:url value="/products/create"/>" method="post" enctype="multipart/form-data">

        <div class="card">
            <div class="card-header"> Registro de Producto</div>

            <div class="card-body">

                <div class="form-group">
                    <label for="name">Nombre</label>
                    <input type="text" name="name" id="name" class="form-control"
                           maxlength="100" required>
                </div>

                <div class="form-group">
                    <label for="categoryId">Categoría</label>
                    <select name="categoryId" id="categoryId" class="form-control" required>
                        <option value="" selected disabled>Seleccione un valor</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>

                    </select>
                </div>

                <div class="form-group">
                    <label for="price">Precio</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">S/</div>
                        </div>
                        <input type="number" name="price" id="price" class="form-control" min="0"
                               step="0.01">
                    </div>
                </div>

                <div class="form-group">
                    <label for="stock">Stock</label>
                    <input type="number" name="stock" id="stock" class="form-control" min="0">
                </div>

                <div class="form-group">
                    <label for="image">Imagen</label>
                    <div class="custom-file">
                        <input type="file" id="image" name="image" class="custom-file-input"/>
                        <label class=" custom-file-label" for="imagen">Seleccionar archivo</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Descripción</label>
                    <textarea name="description" id="description" class="form-control ckeditor"
                              rows="5"></textarea>
                </div>
            </div>
            <div class="card-footer">
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="<c:url value="/products"/>" class="btn btn-secondary">Cancelar</a>
            </div>
        </div>
    </form>
</div>

</body>
</html>
