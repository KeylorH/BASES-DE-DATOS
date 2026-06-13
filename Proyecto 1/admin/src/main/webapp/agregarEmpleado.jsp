<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>

<h1>Agregar Empleados</h1>
<p>Este es el apartado para agregar un empleado nuevo al sistema.</p>


<%    List<logica.Distrito> distritos = (List<logica.Distrito>) session.getAttribute("listaDistritos");
%>
<form class="user" action="SvEmpleados" method="POST">
    <div class="form-group col">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre del empleado" >
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="apellido" name="apellido"
                   placeholder="Apellido del empleado" >
        </div>
        <div class="col-sm-6 mb-3">
            <input type="email" class="form-control form-control-user" id="correo" name="correo"
                   placeholder="Correo electrónico del empleado" >
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="password" name="password"
                   placeholder="Contraseña del empleado" >
        </div>

        <div class="col-sm-6 mb-3">
            <label for="categoria">Distrito del empleado</label>
            <select class="form-control-custom" name="distrito" id="distrito"
                    style="display: block;
                    width: 100%;
                    padding: 0.75rem 1rem;
                    font-size: 0.8rem;
                    color: #6e707e;
                    background-color: #fff;
                    border: 1px solid #d1d3e2;
                    border-radius: 10rem;">
                >
                <option value="">Seleccione una distrito</option>
                <%                    for (logica.Distrito distrito : distritos) {
                %>
                <option value="<%= distrito.getIdDistrito()%>"><%= distrito.getNombreDistrito()%></option>
                <% }%>

            </select>
        </div>

        <button class="btn btn-primary btn-user btn-block" type="submit">
            Crear Empleado
        </button>

    </div>

</form>