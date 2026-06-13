<%@page import="logica.Persona"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="logica.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver clientes</h1>
<p class="mb-4">A continuación podrá visualizar la lista completa de clientes.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Clientes</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre completo</th>
                        <th>Correo electrónico</th>
                        <th>País</th>
                        <th>Cantón</th>
                        <th>Distrito</th>
                        <th style="width: 210px">Acción</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Id</th>
                        <th>Nombre completo</th>
                        <th>Correo electrónico</th>
                        <th>País</th>
                        <th>Cantón</th>
                        <th>Distrito</th>
                        <th style="width: 210px">Acción</th>
                    </tr>
                </tfoot>

                <%                    List<Persona> listaClientes = (List) request.getSession().getAttribute("listaClientes");
                %>

                <tbody>
                    <% if (listaClientes != null) {
                            for (Persona persona : listaClientes) {
                    %>
                    <tr>
                        <td><%=persona.getIdPersona()%></td>
                        <td><%=persona.getNombre() + " " + persona.getApellido()%></td>
                        <td><%=persona.getCorreoElectronico()%></td>
                        <td><%= persona.getDistrito().getCanton().getProvincia().getPais().getNombrePais()%></td>
                        <td><%=persona.getDistrito().getCanton().getNombreCanton()%></td>
                        <td><%=persona.getDistrito().getNombreDistrito()%></td>
                        <td style="display: flex; width: 230px;">
                            <form name="eliminar" action="SvEliminarCliente" method="POST">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color: red; margin-right: 5px;">
                                    <i class="fas fa-trash-alt"></i> Eliminar
                                </button>
                                <input type="hidden" name="idCliente" value="<%=persona.getIdPersona()%>"/>
                            </form>
                            <form name="editar" action="SvEditarCliente" method="GET">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left: 5px;">
                                    <i class="fas fa-pencil-alt"></i> Editar
                                </button>
                                <input type="hidden" name="idCliente" value="<%=persona.getIdPersona()%>"/>
                            </form>
                        </td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay clientes disponibles</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="components/bodyfinal.jsp" %>       

<% String error = (String) request.getAttribute("error");
    if (error != null) {%>
<div class="alert alert-danger">
    <strong>Error:</strong> <%= error%>
</div>
<% }%>