package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DISTRITO")
public class Distrito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISTRITO")
    private int idDistrito;

    @Column(name = "NOMBRE_DISTRITO", length = 20)
    private String nombreDistrito;

    // Relación N → 1 con Canton
    @ManyToOne
    @JoinColumn(name = "ID_CANTON") // FK en la tabla DISTRITO
    private Canton canton;

    // Relación 1 → N con Persona
    @OneToMany(mappedBy = "distrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Persona> personas;

    public Distrito() {
    }

    public Distrito(int idDistrito, String nombreDistrito, Canton canton, List<Persona> personas) {
        this.idDistrito = idDistrito;
        this.nombreDistrito = nombreDistrito;
        this.canton = canton;
        this.personas = personas;
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
    
}
