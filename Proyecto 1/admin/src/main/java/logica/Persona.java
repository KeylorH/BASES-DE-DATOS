package logica;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "PERSONA")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONA")
    private int idPersona;

    @Basic
    @Column(name = "ISEMPLEADO")
    private int isEmpleado; // 0 o 1

    @Basic
    @Column(name = "NOMBRE", length = 20)
    private String nombre;

    @Basic
    @Column(name = "APELLIDO", length = 20)
    private String apellido;

    @Basic
    @Column(name = "PASSWORD_PERSONA", length = 255)
    private String password;

    @Basic
    @Column(name = "CORREO_ELECTRONICO", length = 50)
    private String correoElectronico;

    // Relación N → 1 con Distrito
    @ManyToOne
    @JoinColumn(name = "ID_DISTRITO")  // la columna en la tabla PERSONA
    private Distrito distrito;

    // Relación 1 → N con Factura
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    private List<Factura> facturas;

    public Persona() {
    }

    public Persona(int idPersona, int isEmpleado, String nombre, String apellido, String correoElectronico, String password, int idDistrito, Distrito distrito, List<Factura> facturas) {
        this.idPersona = idPersona;
        this.isEmpleado = isEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.password = password;
        this.distrito = distrito;
        this.facturas = facturas;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIsEmpleado() {
        return isEmpleado;
    }

    public void setIsEmpleado(int isEmpleado) {
        this.isEmpleado = isEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

}
