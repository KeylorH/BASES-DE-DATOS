<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>

<h1>Agregar Categorías</h1>
<p>Este es el apartado para agregar categorías al sistema.</p>

<form class="user" action="SvAccionCategorias" method="POST">
    <div class="form-group col">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre de la categoría"  maxlength="20">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="descripcion" name="descripcion"
                   placeholder="Descripción de la categoría "/>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="number" class="form-control form-control-user"        
                   step="0.01" 
                   min="0.01" 
                   max="1.00" id="impuesto" name="impuesto"
                   placeholder="Impuesto de la categoría" >
        </div>

        <button class="btn btn-primary btn-user btn-block" type="submit">
            Crear Categoría
        </button>

    </div>

</form>


<%@include file="components/bodyfinal.jsp" %>       
