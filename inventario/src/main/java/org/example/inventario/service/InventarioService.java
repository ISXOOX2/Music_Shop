package org.example.inventario.service;

import org.example.inventario.model.Inventario;
import org.example.inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario obtenerPorId(Integer id) {
        try {
            log.info("Buscando registro de inventario con ID: {}", id);
            return inventarioRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Producto no encontrado en inventario con ID: {}", id);
                        return new RuntimeException("Producto no encontrado en el inventario con ID: " + id);
                    });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al buscar inventario con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el inventario con ID: " + id);
        }
    }

    @Transactional
    public Inventario reducirStock(Integer id, Integer cantidadAReducir) {
        try {
            log.info("Iniciando reducción de stock para inventario ID: {}, cantidad: {}", id, cantidadAReducir);
            Inventario inventario = obtenerPorId(id);

            if (inventario.getCantidad() < cantidadAReducir) {
                log.error("Stock insuficiente para inventario ID: {}. Disponible: {}, Solicitado: {}",
                        id, inventario.getCantidad(), cantidadAReducir);
                throw new RuntimeException("Stock insuficiente. Disponible: "
                        + inventario.getCantidad() + ", Solicitado: " + cantidadAReducir);
            }

            inventario.setCantidad(inventario.getCantidad() - cantidadAReducir);
            Inventario actualizado = inventarioRepository.save(inventario);
            log.info("Stock reducido exitosamente. Inventario ID: {}, nuevo stock: {}", id, actualizado.getCantidad());
            return actualizado;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al reducir stock del inventario ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo reducir el stock del inventario con ID: " + id);
        }
    }

    @Transactional
    public Inventario aumentarStock(Integer id, Integer cantidadAAumentar) {
        try {
            log.info("Iniciando aumento de stock para inventario ID: {}, cantidad: {}", id, cantidadAAumentar);
            Inventario inventario = obtenerPorId(id);
            inventario.setCantidad(inventario.getCantidad() + cantidadAAumentar);
            Inventario actualizado = inventarioRepository.save(inventario);
            log.info("Stock aumentado exitosamente. Inventario ID: {}, nuevo stock: {}", id, actualizado.getCantidad());
            return actualizado;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al aumentar stock del inventario ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo aumentar el stock del inventario con ID: " + id);
        }
    }
}