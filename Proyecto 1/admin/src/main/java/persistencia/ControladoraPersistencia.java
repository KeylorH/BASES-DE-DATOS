package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Bitacora;
import logica.Categoria;
import logica.Distrito;
import logica.Factura;
import logica.Persona;
import logica.Producto;
import logica.ProductoBitacoraDTO;
import logica.VentasProvinciaDTO;
import persistencia.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {

    BitacoraJpaController bitacoraJPA = new BitacoraJpaController();
    CategoriaJpaController categoriaJPA = new CategoriaJpaController();
    DistritoJpaController distritoJPA = new DistritoJpaController();
    FacturaJpaController facturaJPA = new FacturaJpaController();
    PersonaJpaController personaJPA = new PersonaJpaController();
    ProductoJpaController productoJPA = new ProductoJpaController();
    ReportesSqlJpaController reporteJPA = new ReportesSqlJpaController();

    public ControladoraPersistencia() {
    }

    //TODO: BORRAR es para pruebas y verificar que la conexión sirva
    public List<Producto> listarProductos() {
        return productoJPA.findProductoEntities();
    }

    public boolean esEmpleado(String nombre, String password) {
        return personaJPA.validarUsuarioEmpleado(nombre, password);
    }

    public List<Producto> getProductos() {
        return productoJPA.findProductoEntities();
    }

    public void eliminarProducto(int idProducto) {
        try {
            productoJPA.destroy(idProducto);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Producto obtenerProducto(int idProducto) {
        return productoJPA.findProducto(idProducto);
    }

    public List<Categoria> obtenerCategorias() {
        return categoriaJPA.findCategoriaEntities();
    }

    public Categoria obtenerCategoria(int idCategoria) {
        return categoriaJPA.findCategoria(idCategoria);
    }

    public void editarProducto(Producto producto) throws Exception {
        productoJPA.edit(producto);
    }

    public void crearBitacora(Bitacora bitacora) {
        bitacoraJPA.create(bitacora);
    }

    public void crearProducto(Producto producto) {
        productoJPA.create(producto);
    }

    public void editarCategoria(Categoria categoria) throws Exception {
        categoriaJPA.edit(categoria);
    }

    public void eliminarCategoria(int idCategoria) throws NonexistentEntityException {
        categoriaJPA.destroy(idCategoria);
    }

    public void crearCategoria(Categoria categoria) {
        categoriaJPA.create(categoria);
    }

    public List<Persona> obtenerClientes() {
        return personaJPA.findPersonasWithAllData();
    }

    public List<Persona> obtenerEmpleados() {
        return personaJPA.findEmpleadosWithAllData();
    }

    public List<Distrito> obtenerDistritos() {
        return distritoJPA.findDistritoEntities();
    }

    public Distrito obtenerDistrito(int idDistrito) {
        return distritoJPA.findDistrito(idDistrito);
    }

    public void crearCliente(Persona persona) {
        personaJPA.create(persona);
    }

    public void crearEmpleado(Persona persona) {
        personaJPA.create(persona);
    }

    public void eliminarCliente(int idCliente) throws NonexistentEntityException {
        personaJPA.destroy(idCliente);
    }

    public void eliminarEmpleado(int idEmpleado) throws NonexistentEntityException {
        personaJPA.destroy(idEmpleado);
    }

    public Persona obtenerCliente(int idCliente) {
        return personaJPA.findPersona(idCliente);
    }

    public void editarCliente(Persona personaOriginal) throws Exception {
        personaJPA.edit(personaOriginal);
    }

    public void editarEmpleado(Persona empleado) throws Exception {
        personaJPA.edit(empleado);
    }

    public List<Factura> obtenerFacturas() {
        return facturaJPA.findFacturasWithAllData();
    }

    public List<Bitacora> obtenerBitacoras() {
        return bitacoraJPA.findBitacorasWithAllData();
    }

    public List<VentasProvinciaDTO> obtenerReporteVentasProvincia() {
        return reporteJPA.obtenerVentasPorProvincia();
    }

    public List<ProductoBitacoraDTO> obtenerReporteProductosBitacora() {
        return reporteJPA.obtenerProductosBitacora();
    }
}
