package logica;

import java.util.Date;

public class ProductoBitacoraDTO {

    private Integer  idProducto;
    private String nombre;
    private String categoria;
    private Date fechaCambio;
    private Double precioAnterior;
    private Double precioNuevo;

    public ProductoBitacoraDTO() {
    }

    public ProductoBitacoraDTO(Integer idProducto, String nombre, String categoria, Date fechaCambio, Double precioAnterior, Double precioNuevo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.fechaCambio = fechaCambio;
        this.precioAnterior = precioAnterior;
        this.precioNuevo = precioNuevo;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(Double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    @Override
    public String toString() {
        return "ProductoBitacoraDTO{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", categoria=" + categoria + ", fechaCambio=" + fechaCambio + ", precioAnterior=" + precioAnterior + ", precioNuevo=" + precioNuevo + '}';
    }

    
}
