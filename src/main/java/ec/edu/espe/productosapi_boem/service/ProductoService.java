package ec.edu.espe.productosapi_boem.service;

import ec.edu.espe.productosapi_boem.exception.ProductoInvalidoException;
import ec.edu.espe.productosapi_boem.exception.ProductoNoEncontradoException;
import ec.edu.espe.productosapi_boem.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private final List<Producto> productosEnMemoria = new ArrayList<>();
    private Long contadorId = 1L;

    public List<Producto> listarProductos() {
        return productosEnMemoria;
    }

    public Producto buscarPorId(Long id) {
        return productosEnMemoria.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto registrarProducto(Producto producto) {
        if (producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio del producto debe ser mayor a cero.");
        }
        if (producto.getStock() < 0) {
            throw new ProductoInvalidoException("El stock no puede ser negativo.");
        }

        producto.setId(contadorId++);
        productosEnMemoria.add(producto);
        return producto;
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarPorId(id);
        productosEnMemoria.remove(producto);
    }
}