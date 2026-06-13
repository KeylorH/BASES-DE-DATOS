<%@page import="java.text.NumberFormat"%>
<%@page import="logica.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp" %>
<%@include file="components/bodyprimeraparte.jsp" %>


<h1 class="h3 mb-2 text-gray-800">Ver productos</h1>
<p class="mb-4">A continuación podrá visualizar la lista completa de los productos.</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Productos</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre Producto</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Categoría</th>
                        <th>Impuesto</th>
                        <th style="width: 210px">Acción</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>Id</th>
                        <th>Nombre Producto</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Categoría</th>
                        <th>Impuesto</th>
                        <th style="width: 210px">Acción</th>
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
                    List<Producto> listaProductos = (List) request.getSession().getAttribute("listaProductos");
                %>

                <tbody>
                    <% if (listaProductos != null) {
                            for (Producto producto : listaProductos) {
                                String precioFormateado = formatoColones.format(producto.getPrecio());
                                String nombreCategoria = "Sin categoría";
                                String impuestoFormateado = "N/A";

                                if (producto.getCategoria() != null) {
                                    nombreCategoria = producto.getCategoria().getDescripcion();
                                    impuestoFormateado = formatoPorcentaje.format(producto.getCategoria().getImpuesto());
                                }

                    %>
                    <tr>
                        <td><%=producto.getIdProducto()%></td>
                        <td><%=producto.getNombre()%></td>
                        <td><%=precioFormateado%></td>
                        <td><%=producto.getCantidad()%></td>
                        <td><%=nombreCategoria %></td>
                        <td><%=impuestoFormateado%></td>
                        <td style="display: flex; width: 230px;">
                            <form name="eliminar" action="SvEliminarProducto" method="POST">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color: red; margin-right: 5px;">
                                    <i class="fas fa-trash-alt"></i> Eliminar
                                </button>
                                <input type="hidden" name="idProducto" value="<%=producto.getIdProducto()%>"/>
                            </form>
                            <form name="editar" action="SvEditarProducto" method="GET">
                                <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left: 5px;">
                                    <i class="fas fa-pencil-alt"></i> Editar
                                </button>
                                <input type="hidden" name="idProducto" value="<%=producto.getIdProducto()%>"/>
                            </form>
                        </td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No hay productos disponibles</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="components/bodyfinal.jsp" %>       