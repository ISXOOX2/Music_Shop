package org.example.inventario.service;

import org.example.inventario.client.ProductoClient;
import org.example.inventario.client.SucursalClient;
import org.example.inventario.dto.InventarioRequestDTO;
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
    private final ProductoClient productoClient;
    private final SucursalClient sucursalClient;

    public InventarioService(InventarioRepository inventarioRepository,
                             ProductoClient productoClient,
                             SucursalClient sucursalClient) {
        this.inventarioRepository = inventarioRepository;
        this.productoClient = productoClient;
        this.sucursalClient = sucursalClient;
    }

    public List<Inventario> listarTodos() {
        log.info("Obteniendo todos los registros de inventario");
        return inventarioRepository.findAll();
    }

    public Inventario obtenerPorId(Integer id) {
        log.info("Buscando registro de inventario con ID: {}", id);
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de inventario no encontrado con ID: " + id));
    }

    public java.util.Optional<Inventario> findById(Integer id) {
        return inventarioRepository.findById(id);
    }

    //Recibe DTO y usa Feign para validar producto y sucursal
    public Inventario crear(InventarioRequestDTO dto) {
        log.info("Intentando crear inventario para Producto ID: {} en Sucursal ID: {}", dto.getProductoId(), dto.getSucursalId());

        if (!productoClient.existeProductoPorId(dto.getProductoId())) {
            log.error("Error: Producto con ID {} no existe.", dto.getProductoId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto especificado no existe en el sistema.");
        }

        if (!sucursalClient.existeSucursalPorId(dto.getSucursalId())) {
            log.error("Error: Sucursal con ID {} no existe.", dto.getSucursalId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sucursal especificada no existe en el sistema.");
        }

        Inventario inventario = new Inventario();
        inventario.setProductoId(dto.getProductoId());
        inventario.setSucursalId(dto.getSucursalId());
        inventario.setCantidadDisponible(dto.getCantidadDisponible());
        inventario.setCantidadReservada(dto.getCantidadReservada() != null ? dto.getCantidadReservada() : 0);

        Inventario guardado = inventarioRepository.save(inventario);
        log.info("Inventario creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    // Alias usado por pruebas unitarias y por compatibilidad
    public Inventario save(InventarioRequestDTO dto) {
        return crear(dto);
    }

    @Transactional
    public Inventario reducirStock(Integer id, Integer cantidadAReducir) {
        log.info("Iniciando reducción de stock para inventario ID: {}", id);
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidadDisponible() < cantidadAReducir) {
            log.error("Stock insuficiente para inventario ID: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente.");
        }

        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidadAReducir);
        return inventarioRepository.save(inventario);
    }

    // Alias usado por las pruebas unitarias (mismo comportamiento que reducirStock)
    public Inventario descontar(Integer id, Integer cantidad) {
        return reducirStock(id, cantidad);
    }

    @Transactional
    public Inventario aumentarStock(Integer id, Integer cantidadAAumentar) {
        log.info("Iniciando aumento de stock para inventario ID: {}", id);
        Inventario inventario = obtenerPorId(id);
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidadAAumentar);
        return inventarioRepository.save(inventario);
    }

    /**
     * Verifica si hay cantidadDisponible suficiente para cubrir la cantidad solicitada,
     * sin modificar el inventario. Usado por Pedido antes de confirmar una compra.
     */
    public boolean verificarStock(Integer id, Integer cantidadSolicitada) {
        log.info("Verificando stock disponible para inventario ID: {}", id);
        Inventario inventario = obtenerPorId(id);
        return inventario.getCantidadDisponible() >= cantidadSolicitada;
    }

    /**
     * Revierte una reserva: mueve unidades de cantidadReservada de vuelta a cantidadDisponible.
     * Se usa, por ejemplo, cuando se cancela un pedido o devolución.
     */
    @Transactional
    public Inventario revertirReserva(Integer id, Integer cantidadARevertir) {
        log.info("Revirtiendo reserva de {} unidades para inventario ID: {}", cantidadARevertir, id);
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidadReservada() < cantidadARevertir) {
            log.error("No se puede revertir más de lo reservado. Inventario ID: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La cantidad a revertir excede la cantidad reservada.");
        }

        inventario.setCantidadReservada(inventario.getCantidadReservada() - cantidadARevertir);
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidadARevertir);

        return inventarioRepository.save(inventario);
    }

    /**
     * Reserva stock: mueve unidades de cantidadDisponible a cantidadReservada.
     * Se usa, por ejemplo, cuando se genera un pedido pendiente de pago.
     */
    @Transactional
    public Inventario reservar(Integer id, Integer cantidadAReservar) {
        log.info("Reservando {} unidades para inventario ID: {}", cantidadAReservar, id);
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidadDisponible() < cantidadAReservar) {
            log.error("Stock disponible insuficiente para reservar. Inventario ID: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock disponible insuficiente para reservar.");
        }

        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidadAReservar);
        inventario.setCantidadReservada(inventario.getCantidadReservada() + cantidadAReservar);

        return inventarioRepository.save(inventario);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando registro de inventario con ID: {}", id);
        inventarioRepository.deleteById(id);
    }

    public boolean existePorId(Integer id) {
        return inventarioRepository.existsById(id);
    }

    public boolean existsById(Integer id) {
        return inventarioRepository.existsById(id);
    }
}