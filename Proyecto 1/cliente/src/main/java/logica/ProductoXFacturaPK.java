package logica;

import java.io.Serializable;
import java.util.Objects;

public class ProductoXFacturaPK implements Serializable {

    private int idProducto;
    private int idFactura;

    public ProductoXFacturaPK() {
    }

    public ProductoXFacturaPK(int idProducto, int idFactura) {
        this.idProducto = idProducto;
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoXFacturaPK)) return false;
        ProductoXFacturaPK that = (ProductoXFacturaPK) o;
        return idProducto == that.idProducto && idFactura == that.idFactura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idFactura);
    }
}
