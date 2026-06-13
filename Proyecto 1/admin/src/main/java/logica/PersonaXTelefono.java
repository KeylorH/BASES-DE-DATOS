package logica;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSONA_X_TELEFONO")
@IdClass(PersonaXTelefonoPK.class)
public class PersonaXTelefono implements Serializable {

    @Id
    @Column(name = "ID_PERSONA")
    private int idPersona;

    @Id
    @Column(name = "ID_TELEFONO")
    private int idTelefono;

    // Relación muchos a uno con Persona
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA", insertable = false, updatable = false)
    private Persona persona;

    // Relación muchos a uno con Telefono
    @ManyToOne
    @JoinColumn(name = "ID_TELEFONO", insertable = false, updatable = false)
    private Telefono telefono;

    public PersonaXTelefono() {
    }

    public PersonaXTelefono(int idPersona, int idTelefono, Persona persona, Telefono telefono) {
        this.idPersona = idPersona;
        this.idTelefono = idTelefono;
        this.persona = persona;
        this.telefono = telefono;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;

        if (persona != null) {
            this.idPersona = persona.getIdPersona();
        }
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
        if (telefono != null) {
            this.idTelefono = telefono.getIdTelefono();
        }
    }

}
