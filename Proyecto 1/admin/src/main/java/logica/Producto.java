package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private int idProducto;

    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Column(name = "PRECIO", precision = 10, scale = 4)
    private double precio;

    @Column(name = "CANTIDAD")
    private int cantidad;

    // Relación N → 1 con Categoria
    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA")
    private Categoria categoria;

    // Relación 1 → N con Bitacora
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bitacora> bitacoras;

    // Relación M → N con Factura, a través de PRODUCTO_X_FACTURA
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductoXFactura> productosXFactura;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, double precio, int cantidad, Categoria categoria, List<Bitacora> bitacoras, List<ProductoXFactura> productosXFactura) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.bitacoras = bitacoras;
        this.productosXFactura = productosXFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public void setBitacoras(List<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    public List<ProductoXFactura> getProductosXFactura() {
        return productosXFactura;
    }

    public void setProductosXFactura(List<ProductoXFactura> productosXFactura) {
        this.productosXFactura = productosXFactura;
    }
    
}
