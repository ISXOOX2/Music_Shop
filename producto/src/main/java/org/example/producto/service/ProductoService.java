package org.example.producto.service;

import org.example.producto.model.Producto;
import org.example.producto.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        try {
            log.info("Obteniendo lista de todos los productos");
            return productoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de productos: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de productos");
        }
    }

    public Optional<Producto> findById(Integer id) {
        try {
            log.info("Buscando producto con ID: {}", id);
            Optional<Producto> resultado = productoRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún producto con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el producto con ID: " + id);
        }
    }

    public List<Producto> findByNombre(String nombre) {
        try {
            log.info("Buscando productos con nombre que contenga: {}", nombre);
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            log.error("Error al buscar productos por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar productos por nombre: " + nombre);
        }
    }

    public List<Producto> findByFormato(String formato) {
        try {
            log.info("Buscando productos con formato: {}", formato);
            return productoRepository.findByFormato(formato);
        } catch (Exception e) {
            log.error("Error al buscar productos con formato '{}': {}", formato, e.getMessage());
            throw new RuntimeException("Error al buscar productos con formato: " + formato);
        }
    }

    public Producto save(Producto producto) {
        try {
            log.info("Guardando producto: {}", producto.getNombre());
            Producto guardado = productoRepository.save(producto);
            log.info("Producto guardado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al guardar producto '{}': {}", producto.getNombre(), e.getMessage());
            throw new RuntimeException("No se pudo guardar el producto: " + producto.getNombre());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando producto con ID: {}", id);
            productoRepository.deleteById(id);
            log.info("Producto con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el producto con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return productoRepository.existsById(id);
    }
}