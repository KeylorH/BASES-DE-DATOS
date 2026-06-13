<%@page import="java.util.List"%>
<%@page import="logica.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>

<h1>Editar Productos</h1>
<p>Este es el apartado para modificar un producto del sistema.</p>

<% Producto producto = (Producto) request.getSession().getAttribute("productoEditar");
    List<logica.Categoria> categorias = (List<logica.Categoria>) session.getAttribute("listaCategorias");
    int idCategoriaActual = (producto.getCategoria() != null) ? producto.getCategoria().getIdCategoria() : -1;
%>

<form class="user" action="SvEditarProducto" method="POST">
    <div class="form-group col">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre del producto" value="<%=producto.getNombre()%>">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="number" step="0.01" class="form-control form-control-user"
                   id="precio" name="precio"
                   placeholder="Precio del producto" value="<%= producto.getPrecio()%>"/>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="number" class="form-control form-control-user" id="cantidad" name="cantidad"
                   placeholder="Cantidad  del producto" value="<%= producto.getCantidad()%>">
        </div>



        <div class="col-sm-6 mb-3">
            <label for="categoria">Categoría del producto</label>
            <select class="form-control-custom" name="categoria" id="categoria"
                    style="display: block;
                    width: 100%;
                    padding: 0.75rem 1rem;
                    font-size: 0.8rem;
                    color: #6e707e;
                    background-color: #fff;
                    border: 1px solid #d1d3e2;
                    border-radius: 10rem;">
                >
                <option value="">Seleccione una categoría</option>
                <%
                    for (logica.Categoria cat : categorias) {
                        boolean selected = Integer.valueOf(cat.getIdCategoria()).equals(Integer.valueOf(idCategoriaActual));
                %>
                <option value="<%= cat.getIdCategoria()%>" <%= selected ? "selected" : ""%>>
                    <%= cat.getNombre()%>
                </option>
                <% }%>

            </select>
        </div>


        <button class="btn btn-primary btn-user btn-block" type="submit">
            Guardar Modificación
        </button>

    </div>

</form>



<%@include file="components/bodyfinal.jsp" %>       