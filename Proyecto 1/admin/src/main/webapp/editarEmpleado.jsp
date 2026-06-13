<%@page import="logica.Distrito"%>
<%@page import="java.util.List"%>
<%@page import="logica.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>

<h1>Editar Empleado</h1>
<p>Este es el apartado para modificar un empleado del sistema.</p>

<% Persona empleado = (Persona) request.getSession().getAttribute("empleadoEditar");
    List<Distrito> distritos = (List<Distrito>) session.getAttribute("listaDistritos");
    int idDistritoActual = (empleado.getDistrito() != null) ? empleado.getDistrito().getIdDistrito() : -1;
%>

<form class="user" action="SvEditarEmpleado" method="POST">
    <div class="form-group col">

        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre del empleado"   value="<%=empleado.getNombre()%>">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="apellido" name="apellido"
                   placeholder="Apellido del empleado"  value="<%=empleado.getApellido()%>">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="email" class="form-control form-control-user" id="correo" name="correo"
                   placeholder="Correo electrónico del empleado"  value="<%=empleado.getCorreoElectronico()%>">
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
                <%
                    for (Distrito distrito : distritos) {
                        boolean selected = Integer.valueOf(distrito.getIdDistrito()).equals(Integer.valueOf(idDistritoActual));
                %>
                <option value="<%= distrito.getIdDistrito()%>" <%= selected ? "selected" : ""%>>
                    <%= distrito.getNombreDistrito()%>
                </option>
                <% }%>

            </select>
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
