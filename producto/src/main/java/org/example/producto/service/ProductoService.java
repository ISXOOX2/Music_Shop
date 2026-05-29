package org.example.producto.service;

import org.example.producto.dto.ProductoRequestDTO;
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
        log.info("Obteniendo lista de todos los productos");
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Integer id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id);
    }

    public List<Producto> findByNombre(String nombre) {
        log.info("Buscando productos con nombre que contenga: {}", nombre);
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> findByFormato(String formato) {
        log.info("Buscando productos con formato: {}", formato);
        return productoRepository.findByFormato(formato);
    }

    //Ahora recibe el DTO para crear un producto de forma segura
    public Producto save(ProductoRequestDTO dto) {
        log.info("Guardando nuevo producto: {}", dto.getNombre());

        // Transformamos el DTO en la Entidad real
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setFormato(dto.getFormato());
        producto.setPrecio(dto.getPrecio());

        Producto guardado = productoRepository.save(producto);
        log.info("Producto guardado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    // Metodo específico para actualizar recibiendo el ID y el DTO
    public Producto update(Integer id, ProductoRequestDTO dto) {
        log.info("Actualizando producto con ID: {}", id);


        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(dto.getNombre());
        producto.setFormato(dto.getFormato());
        producto.setPrecio(dto.getPrecio());

        Producto actualizado = productoRepository.save(producto);
        log.info("Producto con ID: {} actualizado exitosamente", actualizado.getId());
        return actualizado;
    }

    public void delete(Integer id) {
        log.warn("Eliminando producto con ID: {}", id);
        productoRepository.deleteById(id);
        log.info("Producto con ID: {} eliminado correctamente", id);
    }

    public boolean existsById(Integer id) {
        return productoRepository.existsById(id);
    }
}