package logica;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Carrito implements Serializable {

    private Map<Producto, Integer> productos = new HashMap<>();

    // Método para agregar un producto al carrito
    public void agregarProducto(Producto producto) {
        productos.put(producto, productos.getOrDefault(producto, 0) + 1);
    }

    // Método para eliminar un producto del carrito
    public void eliminarProducto(Producto producto) {
        // Verifica si el producto está en el carrito
        if (productos.containsKey(producto)) {
            int cantidad = productos.get(producto);
            if (cantidad > 1) {
                // Si hay más de una unidad, decrementa la cantidad
                productos.put(producto, cantidad - 1);
            } else {
                // Si solo hay una unidad, elimina el producto completamente
                productos.remove(producto);
            }
        }
    }

    // Método para obtener el mapa de productos y cantidades
    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    // Método para calcular el total de la compra
    public double obtenerTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        return total;
    }
}