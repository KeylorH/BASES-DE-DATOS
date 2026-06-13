package logica;

public class VentasProvinciaDTO {

    private String provincia;
    private double totalVentas;

    public VentasProvinciaDTO() {
    }

    public VentasProvinciaDTO(String provincia, double totalVentas) {
        this.provincia = provincia;
        this.totalVentas = totalVentas;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    @Override
    public String toString() {
        return "VentasProvinciaDTO{" + "provincia=" + provincia + ", totalVentas=" + totalVentas + '}';
    }
    
    
}
