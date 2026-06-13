package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "METODO_PAGO")
public class MetodoPago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_METODO")
    private int idMetodo;

    @Column(name = "NOMBRE", length = 10)
    private String nombre;

    // Relación 1 → N con Factura
    @OneToMany(mappedBy = "metodoPago", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Factura> facturas;

    public MetodoPago() {
    }

    public MetodoPago(int idMetodo, String nombre, List<Factura> facturas) {
        this.idMetodo = idMetodo;
        this.nombre = nombre;
        this.facturas = facturas;
    }

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
}
