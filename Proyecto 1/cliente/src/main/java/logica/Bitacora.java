package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BITACORA")

public class Bitacora implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA")
    private int idBitacora;

    // Relación N → 1 con Producto
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @Column(name = "PRECIO_ANTERIOR", precision = 10, scale = 4)
    private double precioAnterior;

    @Column(name = "PRECIO_NUEVO", precision = 10, scale = 4)
    private double precioNuevo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CAMBIO")
    private Date fechaCambio;

    public Bitacora() {
    }

    public Bitacora(int idBitacora, Producto producto, double precioAnterior, double precioNuevo, Date fechaCambio) {
        this.idBitacora = idBitacora;
        this.producto = producto;
        this.precioAnterior = precioAnterior;
        this.precioNuevo = precioNuevo;
        this.fechaCambio = fechaCambio;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
