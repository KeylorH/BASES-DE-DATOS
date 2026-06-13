package logica;

import java.io.Serializable;
import java.util.Objects;

public class PersonaXTelefonoPK implements Serializable {

    private int idPersona;
    private int idTelefono;

    public PersonaXTelefonoPK() {
    }

    public PersonaXTelefonoPK(int idPersona, int idTelefono) {
        this.idPersona = idPersona;
        this.idTelefono = idTelefono;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaXTelefonoPK)) {
            return false;
        }
        PersonaXTelefonoPK that = (PersonaXTelefonoPK) o;
        return idPersona == that.idPersona && idTelefono == that.idTelefono;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona, idTelefono);
    }
}
