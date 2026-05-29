package org.example.inventario.service;

import org.example.inventario.model.Inventario;
import org.example.inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    //Listar todos
    public List<Inventario> listarTodos() {
        log.info("Obteniendo todos los registros de inventario");
        return inventarioRepository.findAll();
    }

    //Crear inventario
    public Inventario crear(Inventario inventario) {
        log.info("Creando nuevo registro de inventario");
        Inventario guardado = inventarioRepository.save(inventario);
        log.info("Inventario creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public Inventario obtenerPorId(Integer id) {
        log.info("Buscando registro de inventario con ID: {}", id);
        return inventarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado en inventario con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de inventario no encontrado con ID: " + id);
                });
    }

    @Transactional
    public Inventario reducirStock(Integer id, Integer cantidadAReducir) {
        log.info("Iniciando reducción de stock para inventario ID: {}, cantidad a reducir: {}", id, cantidadAReducir);
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidad() < cantidadAReducir) {
            log.error("Stock insuficiente para inventario ID: {}. Disponible: {}, Solicitado: {}",
                    id, inventario.getCantidad(), cantidadAReducir);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente. Cantidad disponible: "
                    + inventario.getCantidad() + ", Solicitada: " + cantidadAReducir);
        }

        inventario.setCantidad(inventario.getCantidad() - cantidadAReducir);
        Inventario actualizado = inventarioRepository.save(inventario);
        log.info("Stock reducido exitosamente. Inventario ID: {}, nuevo stock: {}", id, actualizado.getCantidad());
        return actualizado;
    }

    @Transactional
    public Inventario aumentarStock(Integer id, Integer cantidadAAumentar) {
        log.info("Iniciando aumento de stock para inventario ID: {}, cantidad a aumentar: {}", id, cantidadAAumentar);
        Inventario inventario = obtenerPorId(id);

        inventario.setCantidad(inventario.getCantidad() + cantidadAAumentar);
        Inventario actualizado = inventarioRepository.save(inventario);
        log.info("Stock aumentado exitosamente. Inventario ID: {}, nuevo stock: {}", id, actualizado.getCantidad());
        return actualizado;
    }

    //Eliminar
    public void eliminar(Integer id) {
        log.info("Eliminando registro de inventario con ID: {}", id);
        inventarioRepository.deleteById(id);
    }

    //Verificar existencia
    public boolean existePorId(Integer id) {
        log.info("Verificando existencia de inventario ID: {}", id);
        return inventarioRepository.existsById(id);
    }
}