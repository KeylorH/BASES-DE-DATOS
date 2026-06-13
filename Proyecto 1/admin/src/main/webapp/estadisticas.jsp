<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.VentasProvinciaDTO"%>
<%@page import="logica.ProductoBitacoraDTO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver reportes</h1>
<p class="mb-4">A continuación podrá visualizar los reportes de ElectroTech.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Total de ventas</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Provincia</th>
                        <th>Total ventas</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Provincia</th>
                        <th>Total ventas</th>
                    </tr>
                </tfoot>

                <%                    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "CR"));
                    simbolos.setGroupingSeparator(',');
                    simbolos.setDecimalSeparator('.');
                    DecimalFormat formatoColones = new DecimalFormat("₡#,##0.00", simbolos);

                    NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
                    formatoPorcentaje.setMinimumFractionDigits(0);
                %>

                <%                    List<VentasProvinciaDTO> ventas = (List) request.getSession().getAttribute("reporteVentasProvincia");
                %>

                <%
                    // Creamos un formateador de fecha con el patrón deseado
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CR"));
                %>

                <tbody>
                    <% if (ventas != null) {
                            for (VentasProvinciaDTO v : ventas) {
                                String precioFormateado = formatoColones.format(v.getTotalVentas());
                    %>
                    <tr>
                        <td><%= v.getProvincia()%></td>
                        <td><%= precioFormateado%></td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay ventas disponibles</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<hr class="sidebar-divider">

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Productos y Bitácora</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Fecha cambio</th>
                        <th>Precio anterior</th>
                        <th>Precio nuevo</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Fecha cambio</th>
                        <th>Precio anterior</th>
                        <th>Precio nuevo</th>
                    </tr>
                </tfoot>
                <tbody>
                    <%
                        List<ProductoBitacoraDTO> det = (List<ProductoBitacoraDTO>) session.getAttribute("reporteProductosBitacora");
                    %>
                    <% if (det != null) {
                            for (ProductoBitacoraDTO p : det) {
                                String precioAnteriorFormateado = (p.getPrecioAnterior() != null)
                                        ? formatoColones.format(p.getPrecioAnterior())
                                        : "-";
                                String precioNuevoFormateado = (p.getPrecioNuevo() != null)
                                        ? formatoColones.format(p.getPrecioNuevo())
                                        : "-";
                                String fechaFormateada = (p.getFechaCambio() != null)
                                        ? sdf.format(p.getFechaCambio())
                                        : "-";
                    %>
                    <tr>
                        <td><%= p.getIdProducto()%></td>
                        <td><%= p.getNombre()%></td>
                        <td><%= p.getCategoria()%></td>
                        <td><%= fechaFormateada%></td>
                        <td><%= precioAnteriorFormateado%></td>
                        <td><%= precioNuevoFormateado%></td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay productos ni registros de bitácora disponibles</td>
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