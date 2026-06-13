<%@page import="logica.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>

<h1>Editar Categoría</h1>
<p>Este es el apartado para modificar una categoría del sistema.</p>

<% Categoria categoria = (Categoria) request.getSession().getAttribute("categoriaEditar");
%>

<form class="user" action="SvEditarCategoria" method="POST">
    <div class="form-group col">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre de la categoría" value="<%=categoria.getNombre()%>" maxlength="20">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="descripcion" name="descripcion"
                   placeholder="Descripción de la categoría " value="<%= categoria.getDescripcion()%>"/>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="number" class="form-control form-control-user" step="0.01" id="impuesto" name="impuesto"
                   placeholder="Impuesto de la categoría" value="<%= categoria.getImpuesto()%>">
        </div>


        <% String error = (String) request.getAttribute("error");
            if (error != null) {%>
        <div class="alert alert-danger">
            <strong>Error:</strong> <%= error%>
        </div>
        <hr class="sidebar-divider">
        <% }%>

        <button class="btn btn-primary btn-user btn-block" type="submit">
            Guardar Modificación
        </button>

    </div>

</form>


<%@include file="components/bodyfinal.jsp" %>       