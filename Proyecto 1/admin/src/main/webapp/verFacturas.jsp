<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Factura"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver facturas</h1>
<p class="mb-4">A continuación podrá visualizar la lista completa de las facturas.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Facturas</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Cliente</th>
                        <th>Método de pago</th>
                        <th>Total</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Id</th>
                        <th>Cliente</th>
                        <th>Método de pago</th>
                        <th>Total</th>
                        <th>Fecha</th>
                    </tr>
                </tfoot>

                <%                    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "CR"));
                    simbolos.setGroupingSeparator(',');
                    simbolos.setDecimalSeparator('.');
                    DecimalFormat formatoColones = new DecimalFormat("₡#,##0.00", simbolos);

                    NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
                    formatoPorcentaje.setMinimumFractionDigits(0);
                %>

                <%                    List<Factura> listaFacturas = (List) request.getSession().getAttribute("listaFacturas");
                %>

                <%
                    // Creamos un formateador de fecha con el patrón deseado
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CR"));
                %>

                <tbody>
                    <% if (listaFacturas != null) {
                            for (Factura factura : listaFacturas) {
                                String precioFormateado = formatoColones.format(factura.getTotal());
                                String fechaFormateada = sdf.format(factura.getFecha());
                    %>
                    <tr>
                        <td><%=factura.getIdFactura()%></td>
                        <td><%=factura.getPersona().getNombre() + " " + factura.getPersona().getApellido()%></td>
                        <td><%=factura.getMetodoPago().getNombre()%></td>
                        <td><%= precioFormateado%></td>
                        <td><%=fechaFormateada%></td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay facturas disponibles</td>
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