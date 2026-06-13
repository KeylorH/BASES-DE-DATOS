<%@page import="logica.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver categorías</h1>
<p class="mb-4">A continuación podrá visualizar la lista completa de categorías.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Categorías</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre Producto</th>
                        <th>Descripción</th>
                        <th>Impuesto</th>
                        <th style="width: 210px">Acción</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Id</th>
                        <th>Nombre Producto</th>
                        <th>Descripción</th>
                        <th>Impuesto</th>
                        <th style="width: 210px">Acción</th>
                    </tr>
                </tfoot>

                <%                    NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
                    formatoPorcentaje.setMinimumFractionDigits(0);
                %>

                <%
                    List<Categoria> listaCategorias = (List) request.getSession().getAttribute("listaCategorias");
                %>

                <tbody>
                    <% if (listaCategorias != null) {
                            for (Categoria categoria : listaCategorias) {
                                String impuestoFormateado = formatoPorcentaje.format(categoria.getImpuesto());
                    %>
                    <tr>
                        <td><%=categoria.getIdCategoria()%></td>
                        <td><%=categoria.getNombre()%></td>
                        <td><%=categoria.getDescripcion()%></td>
                        <td><%=impuestoFormateado%></td>
                        <td style="display: flex; width: 230px;">
                            <form name="eliminar" action="SvEliminarCategoria" method="POST">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color: red; margin-right: 5px;">
                                    <i class="fas fa-trash-alt"></i> Eliminar
                                </button>
                                <input type="hidden" name="idCategoria" value="<%=categoria.getIdCategoria()%>"/>
                            </form>
                            <form name="editar" action="SvEditarCategoria" method="GET">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left: 5px;">
                                    <i class="fas fa-pencil-alt"></i> Editar
                                </button>
                                <input type="hidden" name="idCategoria" value="<%=categoria.getIdCategoria()%>"/>
                            </form>
                        </td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay categorías disponibles</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>


<% String error = (String) request.getAttribute("error");
    if (error != null) {%>
<div class="alert alert-danger">
    <strong>Error:</strong> <%= error%>
</div>
<% }%>


<%@include file="components/bodyfinal.jsp" %>       
