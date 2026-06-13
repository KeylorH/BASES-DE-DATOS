package logica;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TELEFONO")
public class Telefono implements Serializable {

      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONO")
    private int idTelefono;

    @Column(name = "NUMERO", length = 20)
    private String numero;

    public Telefono() {
    }

    public Telefono(int idTelefono, String numero) {
        this.idTelefono = idTelefono;
        this.numero = numero;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
}
