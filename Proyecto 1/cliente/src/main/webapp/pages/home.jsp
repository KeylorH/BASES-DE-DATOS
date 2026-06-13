<%@page import="java.text.NumberFormat"%>
<%@page import="logica.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ElectroTech</title>
        <!-- TailwindCSS imports -->
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    </head>
    <body>
        <main class="flex flex-col w-full h-screen pt-[112px]">
            <header class="absolute inset-x-0 top-0 z-50">
                <nav class="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
                    <div class="flex lg:flex-1">
                        <a href="pages/audio.jsp" class="-m-1.5 p-1.5">
                            <img class="h-8 w-auto" src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600" alt="">
                        </a>
                    </div>
                    <div class="hidden lg:flex lg:gap-x-12">
                        <a href="#" class="text-sm/6 font-semibold text-gray-900">Inicio</a>
                        <div class="group relative inline-block">
                            <a href="pages/productos.jsp" class="text-sm/6 font-semibold text-gray-900">Productos</a>

                            <div class="invisible flex opacity-0 group-hover:visible group-hover:opacity-100 transition-all duration-200 absolute left-1/2 -translate-x-1/2 mt-2 w-max bg-white shadow-lg rounded-lg z-10">
                                <div class="grid grid-cols-5 grid-rows-2 gap-4">
                                    <a href="pages/laptops.jsp" class="flex justify-center items-center text-sm font-semibold text-gray-900 p-4 w-fit">Laptops</a>
                                    <a href="pages/desktops.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Desktops</a>
                                    <a href="pages/componentes.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Componentes</a>
                                    <a href="pages/perifericos.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Periféricos</a>
                                    <a href="pages/accesorios.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Accesorios</a>
                                    <a href="pages/redes.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Redes</a>
                                    <a href="pages/software.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Software</a>
                                    <a href="pages/gaming.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Gaming</a>
                                    <a href="pages/audio.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Audio</a>
                                    <a href="pages/almacenamiento.jsp" class="text-sm font-semibold text-gray-900 p-4 w-fit">Almacenamiento</a>
                                </div>
                            </div>
                        </div>
                        <a href="#" class="text-sm/6 font-semibold text-gray-900">Facturas</a>
                    </div>
                    <div class="hidden lg:flex lg:flex-1 lg:justify-end">
                        <a href="#" class="text-sm/6 font-semibold text-gray-900">Username</a>
                    </div>
                </nav>
            </header>
            <div class="grid grid-cols-[200px_1fr] w-[900px] self-center gap-x-2">
                <aside class="flex flex-col">
                    <h3 class="font-sans text-xs font-medium text-gray-500 px-3">CATEGORIAS</h3>
                    <div class="flex flex-col pt-4">
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="productos.jsp?categoria=2" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="#" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                    </div>
                </aside>
                <div class="bg-white">
                    <div class="w-full">
                        <h2 class="text-2xl font-semibold tracking-tight text-gray-900">Productos</h2>
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

                        <div class="mt-6 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
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
                            <div class="group relative">
                                <img src="https://tailwindcss.com/plus-assets/img/ecommerce-images/product-page-01-related-product-01.jpg" alt="Front of men&#039;s Basic Tee in black." class="aspect-square w-full rounded-md bg-gray-200 object-cover group-hover:opacity-75 lg:aspect-auto lg:h-40">
                                <div class="mt-4 flex justify-between">
                                    <div>
                                        <h3 class="text-sm text-gray-700 truncate">
                                            <a href="#">
                                                <span aria-hidden="true" class="absolute inset-0"></span>
                                                <%=producto.getNombre()%>
                                            </a>
                                        </h3>
                                    </div>
                                </div>
                                <p class="text-sm font-medium text-gray-900"><%=precioFormateado%></p>
                                <p class="text-sm font-medium text-gray-900"><%=producto.getCategoria().getIdCategoria()%></p>
                            </div>
                            <%     }
                            } else { %>
                            <span>No hay productos disponibles</span>
                            <% }%>

                            <!-- More products... -->
                        </div>
                    </div>
                </div> 
            </div>
        </main>
    </body>
</html>
