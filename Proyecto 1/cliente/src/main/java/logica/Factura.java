package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FACTURA")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FACTURA")
    private int idFactura;

    // Relación N → 1 con Persona
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA")
    private Persona persona;

    // Relación N → 1 con MetodoPago
    @ManyToOne
    @JoinColumn(name = "ID_METODO")
    private MetodoPago metodoPago;

    @Column(name = "TOTAL", precision = 10, scale = 2)
    private double total;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;

    // Relación M → N con Producto usando la tabla intermedia PRODUCTO_X_FACTURA
    // Mapearemos con la clase intermedia ProductoXFactura
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoXFactura> productosXFactura;

    // Constructor vacío
    public Factura() {
    }

    // Constructor con parámetros
    public Factura(int idFactura, Persona persona, MetodoPago metodoPago, double total, Date fecha, List<ProductoXFactura> productosXFactura) {
        this.idFactura = idFactura;
        this.persona = persona;
        this.metodoPago = metodoPago;
        this.total = total;
        this.fecha = fecha;
        this.productosXFactura = productosXFactura;
    }

    // Getters y setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ProductoXFactura> getProductosXFactura() {
        return productosXFactura;
    }

    public void setProductosXFactura(List<ProductoXFactura> productosXFactura) {
        this.productosXFactura = productosXFactura;
    }

    // Método toString() para mostrar la información de la factura
    @Override
    public String toString() {
        return "Factura{" +
               "idFactura=" + idFactura +
               ", persona=" + persona +
               ", metodoPago=" + metodoPago +
               ", total=" + total +
               ", fecha=" + fecha +
               '}';
    }
}
