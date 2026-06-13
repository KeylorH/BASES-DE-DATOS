package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CANTON")
public class Canton implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CANTON")
    private int idCanton;

    @Column(name = "NOMBRE_CANTON", length = 20)
    private String nombreCanton;

    // Relación N → 1 con Provincia
    @ManyToOne
    @JoinColumn(name = "ID_PROVINCIA") // FK en la tabla CANTON
    private Provincia provincia;

    // Relación 1 → N con Distrito
    @OneToMany(mappedBy = "canton", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Distrito> distritos;

    public Canton() {
    }

    public Canton(int idCanton, String nombreCanton, Provincia provincia, List<Distrito> distritos) {
        this.idCanton = idCanton;
        this.nombreCanton = nombreCanton;
        this.provincia = provincia;
        this.distritos = distritos;
    }

    public int getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(int idCanton) {
        this.idCanton = idCanton;
    }

    public String getNombreCanton() {
        return nombreCanton;
    }

    public void setNombreCanton(String nombreCanton) {
        this.nombreCanton = nombreCanton;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(List<Distrito> distritos) {
        this.distritos = distritos;
    }
    
}
