<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Bitacora"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver Bitácoras</h1>
<p class="mb-4">A continuación podrá visualizar la lista completa de las bitácoras.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Bitácoras</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre del producto</th>
                        <th>Precio anterior</th>
                        <th>Precio nuevo</th>
                        <th>Fecha de cambio</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Id</th>
                        <th>Nombre del producto</th>
                        <th>Precio anterior</th>
                        <th>Precio nuevo</th>
                        <th>Fecha de cambio</th>
                    </tr>
                </tfoot>

                <%                    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "CR"));
                    simbolos.setGroupingSeparator(',');
                    simbolos.setDecimalSeparator('.');
                    DecimalFormat formatoColones = new DecimalFormat("₡#,##0.00", simbolos);

                    NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
                    formatoPorcentaje.setMinimumFractionDigits(0);
                %>

                <%
                    List<Bitacora> listaBitacoras = (List) request.getSession().getAttribute("listaBitacoras");
                %>

                <%
                    // Creamos un formateador de fecha con el patrón deseado
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CR"));
                %>

                <tbody>
                    <% if (listaBitacoras != null) {
                            for (Bitacora bitacora : listaBitacoras) {
                                String precioAnteriorFormateado = formatoColones.format(bitacora.getPrecioAnterior());
                                String precioNuevoFormateado = formatoColones.format(bitacora.getPrecioNuevo());
                                String fechaFormateada = sdf.format(bitacora.getFechaCambio());
                    %>
                    <tr>
                        <td><%=bitacora.getIdBitacora()%></td>
                        <td><%=bitacora.getProducto().getNombre()%></td>
                        <td><%=precioAnteriorFormateado%></td>
                        <td><%= precioNuevoFormateado%></td>
                        <td><%=fechaFormateada%></td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay bitácoras disponibles</td>
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