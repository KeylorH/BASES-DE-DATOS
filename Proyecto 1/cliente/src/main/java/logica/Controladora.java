package logica;

import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import persistencia.ControladoraPersistencia;
import persistencia.exceptions.NonexistentEntityException;

public class Controladora {

    ControladoraPersistencia controladoraPersis = new ControladoraPersistencia();

    public List<Producto> listarProductos() {
        return controladoraPersis.listarProductos();
    }

    public boolean esEmpleado(String nombre, String password) {
        return controladoraPersis.esEmpleado(nombre, password);
    }

    public void registrarPersona(Persona persona) {
        controladoraPersis.registrarPersona(persona);
    }

    public List<Producto> getProductos() {
        return controladoraPersis.getProductos();
    }

    public void eliminarProducto(int idProducto) {
        controladoraPersis.eliminarProducto(idProducto);
    }

    public Producto obtenerProducto(int idProducto) {
        return controladoraPersis.obtenerProducto(idProducto);
    }

    public List<Categoria> obtenerCategorias() {
        return controladoraPersis.obtenerCategorias();
    }

    public Categoria obtenerCategoria(int idCategoria) {
        return controladoraPersis.obtenerCategoria(idCategoria);
    }

    public void editarProducto(Producto producto) throws Exception {
        controladoraPersis.editarProducto(producto);
    }

    public void registrarBitacora(Producto producto, double precioAnterior, double precioNuevo) {
        Bitacora bitacora = new Bitacora();
        bitacora.setProducto(producto);
        bitacora.setPrecioAnterior(precioAnterior);
        bitacora.setPrecioNuevo(precioNuevo);
        bitacora.setFechaCambio(new Date());

        controladoraPersis.crearBitacora(bitacora);
    }

    public void crearProducto(String nombre, double precio, int cantidad, int idCategoria) {

        Producto producto = new Producto();
        Categoria categoria = obtenerCategoria(idCategoria);

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setCategoria(categoria);

        controladoraPersis.crearProducto(producto);
    }

    public List<Producto> obtenerProductosPorCategoria(int idCategoria) {
        return controladoraPersis.obtenerProductosPorCategoria(idCategoria);
    }

    public void editarCategoria(Categoria categoria) throws Exception {
        controladoraPersis.editarCategoria(categoria);
    }

    public void eliminarCategoria(int idCategoria) throws Exception {
        controladoraPersis.eliminarCategoria(idCategoria);
    }

    public void crearCategoria(String nombreNuevo, String descripcionNueva, double impuestoNuevo) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombreNuevo);
        categoria.setDescripcion(descripcionNueva);
        categoria.setImpuesto(impuestoNuevo);

        controladoraPersis.crearCategoria(categoria);
    }

    public List<Persona> obtenerClientes() {
        return controladoraPersis.obtenerClientes();
    }

    public List<Persona> obtenerEmpleados() {
        return controladoraPersis.obtenerEmpleados();
    }

    public List<Distrito> obtenerDistritos() {
        return controladoraPersis.obtenerDistritos();
    }

    public void crearCliente(String nombre, String apellido, String correo, int idDistrito) {
        Persona persona = new Persona();
        Distrito distrito = obtenerDistrito(idDistrito);

        persona.setIsEmpleado(0); // es un cliente

        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setCorreoElectronico(correo);
        persona.setDistrito(distrito);

        controladoraPersis.crearCliente(persona);
    }

    public void crearEmpleado(String nombre, String apellido, String correo, int idDistrito, String password) {
        Persona persona = new Persona();
        Distrito distrito = obtenerDistrito(idDistrito);

        persona.setIsEmpleado(1); // es un empleado

        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setCorreoElectronico(correo);
        persona.setDistrito(distrito);
        persona.setPassword(password);

        controladoraPersis.crearEmpleado(persona);
    }

    public Distrito obtenerDistrito(int idDistrito) {
        return controladoraPersis.obtenerDistrito(idDistrito);
    }

    public void eliminarCliente(int idCliente) throws NonexistentEntityException {
        controladoraPersis.eliminarCliente(idCliente);
    }

    public void eliminarEmpleado(int idEmpleado) throws NonexistentEntityException {
        controladoraPersis.eliminarEmpleado(idEmpleado);
    }

    public Persona obtenerCliente(int idCliente) {
        return controladoraPersis.obtenerCliente(idCliente);
    }

    public void editarCliente(Persona personaOriginal) throws Exception {
        controladoraPersis.editarCliente(personaOriginal);
    }

    public void editarEmpleado(Persona empleado) throws Exception {
        controladoraPersis.editarEmpleado(empleado);
    }

    public List<Factura> obtenerFacturas() {
        return controladoraPersis.obtenerFacturas();
    }

    public List<Bitacora> obtenerBitacoras() {
        return controladoraPersis.obtenerBitacoras();
    }

    public List<VentasProvinciaDTO> obtenerReporteVentasProvincia() {
        return controladoraPersis.obtenerReporteVentasProvincia();
    }

    public boolean verificarCorreoExistente(String correo) {
        return controladoraPersis.verificarCorreoExistente(correo);
    }

    public boolean esCliente(String correo, String password) {
        return controladoraPersis.esCliente(correo, password);
    }

    public Persona buscarPersonaPorEmail(String email) {
        return controladoraPersis.buscarPersonaPorEmail(email);
    }

    public void crearFactura(Factura factura) {
        controladoraPersis.crearFactura(factura);
    }

    public void crearProductoXFactura(ProductoXFactura productoXFactura) throws Exception {
        controladoraPersis.crearProductoXFactura(productoXFactura);
    }

}
