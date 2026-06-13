package logica;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PAIS")
public class Pais implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAIS")
    private int idPais;
    
    @Column(name = "NOMBRE_PAIS", length = 20)
    private String nombrePais;
    
    // Relación 1 a muchos con Provincia
    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Provincia> provincias;

    public Pais() {
    }

    public Pais(int idPais, String nombrePais, List<Provincia> provincias) {
        this.idPais = idPais;
        this.nombrePais = nombrePais;
        this.provincias = provincias;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }
    
    
}
