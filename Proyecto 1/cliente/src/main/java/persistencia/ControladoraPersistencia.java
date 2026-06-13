package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.*;
import persistencia.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {

    private BitacoraJpaController bitacoraJPA = new BitacoraJpaController();
    private CategoriaJpaController categoriaJPA = new CategoriaJpaController();
    private DistritoJpaController distritoJPA = new DistritoJpaController();
    private FacturaJpaController facturaJPA = new FacturaJpaController();
    private PersonaJpaController personaJPA = new PersonaJpaController();
    private ProductoJpaController productoJPA = new ProductoJpaController();
    private ReportesSqlJpaController reporteJPA = new ReportesSqlJpaController();
    private ProductoXFacturaJpaController productoXFacturaJPA = new ProductoXFacturaJpaController();

    public ControladoraPersistencia() {
    }

    // Método para verificar si el correo electrónico ya existe
    public boolean verificarCorreoExistente(String email) {
        return personaJPA.validarEmailExistente(email);
    }

    public boolean esCliente(String correo, String password) {
        return personaJPA.validarUsuarioCliente(correo, password);
    }

    // Resto de los métodos
    public List<Producto> listarProductos() {
        return productoJPA.findProductoEntities();
    }

    public boolean esEmpleado(String nombre, String password) {
        return personaJPA.validarUsuarioEmpleado(nombre, password);
    }

    public void registrarPersona(Persona persona) {
        personaJPA.create(persona);
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

    public List<Producto> obtenerProductosPorCategoria(int idCategoria) {
        return productoJPA.findProductosPorCategoria(idCategoria);
    }

    public Persona buscarPersonaPorEmail(String email) {
        EntityManager em = personaJPA.getEntityManager();
        TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p WHERE p.correoElectronico = :email", Persona.class);
        query.setParameter("email", email);
        List<Persona> personas = query.getResultList();
        return personas.isEmpty() ? null : personas.get(0);
    }

    public void crearFactura(Factura factura) {
        facturaJPA.create(factura);
    }

    public void crearProductoXFactura(ProductoXFactura productoXFactura) throws Exception {
        productoXFacturaJPA.create(productoXFactura);
    }
}
