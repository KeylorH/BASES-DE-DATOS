package pruebas_controller;

import java.util.List;
import logica.Controladora;
import logica.ProductoBitacoraDTO;
import logica.VentasProvinciaDTO;

public class TEST {

    public static void main(String[] args) {
        try {
            System.out.println("HOLAA MUNDO");

            Controladora control = new Controladora();

            List<VentasProvinciaDTO> ventas = control.obtenerReporteVentasProvincia();
            for (VentasProvinciaDTO v : ventas) {
                System.out.println("ID: " + v.toString());
            }

            System.out.println("***********************************************************************************************");
            System.out.println("***********************************************************************************************");

            List<ProductoBitacoraDTO> productos = control.obtenerReporteProductosBitacora();
            for (ProductoBitacoraDTO v : productos) {
                System.out.println("ID: " + v.toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();          // o usa un logger
        }
    }
}
