package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PROVINCIA")
public class Provincia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVINCIA")
    private int idProvincia;

    @Column(name = "NOMBRE_PROVINCIA", length = 20)
    private String nombreProvincia;

    // Relación N → 1 con Pais
    @ManyToOne
    @JoinColumn(name = "ID_PAIS")  // FK en la tabla PROVINCIA
    private Pais pais;
    
    // Relación 1 → N con Canton
    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Canton> cantones;

    public Provincia() {
    }

    public Provincia(int idProvincia, String nombreProvincia, Pais pais, List cantones) {
        this.idProvincia = idProvincia;
        this.nombreProvincia = nombreProvincia;
        this.pais = pais;
        this.cantones = cantones;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List getCantones() {
        return cantones;
    }

    public void setCantones(List cantones) {
        this.cantones = cantones;
    }
}
