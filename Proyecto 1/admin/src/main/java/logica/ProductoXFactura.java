package logica;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCTO_X_FACTURA")
@IdClass(ProductoXFacturaPK.class)
public class ProductoXFactura implements Serializable {

    @Id
    @Column(name = "ID_PRODUCTO")
    private int idProducto;

    @Id
    @Column(name = "ID_FACTURA")
    private int idFactura;

    @Column(name = "CANTIDAD_PRODUCTO")
    private int cantidadProducto;

    @Column(name = "PRECIO_UNITARIO", precision = 10, scale = 4)
    private double precioUnitario;

    // Relación muchos a uno con Producto
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO", insertable = false, updatable = false)
    private Producto producto;

    // Relación muchos a uno con Factura
    @ManyToOne
    @JoinColumn(name = "ID_FACTURA", insertable = false, updatable = false)
    private Factura factura;

    public ProductoXFactura() {
    }

    public ProductoXFactura(int idProducto, int idFactura, int cantidadProducto, double precioUnitario, Producto producto, Factura factura) {
        this.idProducto = idProducto;
        this.idFactura = idFactura;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.factura = factura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null) {
            this.idProducto = producto.getIdProducto();
        }
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
        if (factura != null) {
            this.idFactura = factura.getIdFactura();
        }
    }

    public ProductoXFacturaPK getProductoXFacturaPK() {
        return new ProductoXFacturaPK(this.idProducto, this.idFactura);
    }

}
