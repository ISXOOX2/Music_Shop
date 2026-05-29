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

    // Inyectamos el repositorio y los clientes Feign
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

    // MODIFICADO: Recibe DTO y usa Feign para validar
    public Inventario crear(InventarioRequestDTO dto) {
        log.info("Intentando crear inventario para Producto ID: {} en Sucursal ID: {}", dto.getProductoId(), dto.getSucursalId());

        // 1. Validar existencia del Producto en el otro microservicio
        if (!productoClient.existeProductoPorId(dto.getProductoId())) {
            log.error("Error: Producto con ID {} no existe.", dto.getProductoId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto especificado no existe en el sistema.");
        }

        // 2. Validar existencia de la Sucursal en el otro microservicio
        if (!sucursalClient.existeSucursalPorId(dto.getSucursalId())) {
            log.error("Error: Sucursal con ID {} no existe.", dto.getSucursalId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sucursal especificada no existe en el sistema.");
        }

        // 3. Si todo es válido, convertimos el DTO a Entidad y guardamos
        Inventario inventario = new Inventario();
        inventario.setProductoId(dto.getProductoId());
        inventario.setSucursalId(dto.getSucursalId());
        inventario.setCantidad(dto.getCantidad());

        Inventario guardado = inventarioRepository.save(inventario);
        log.info("Inventario creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    @Transactional
    public Inventario reducirStock(Integer id, Integer cantidadAReducir) {
        log.info("Iniciando reducción de stock para inventario ID: {}", id);
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidad() < cantidadAReducir) {
            log.error("Stock insuficiente para inventario ID: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente.");
        }

        inventario.setCantidad(inventario.getCantidad() - cantidadAReducir);
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario aumentarStock(Integer id, Integer cantidadAAumentar) {
        log.info("Iniciando aumento de stock para inventario ID: {}", id);
        Inventario inventario = obtenerPorId(id);
        inventario.setCantidad(inventario.getCantidad() + cantidadAAumentar);
        return inventarioRepository.save(inventario);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando registro de inventario con ID: {}", id);
        inventarioRepository.deleteById(id);
    }

    public boolean existePorId(Integer id) {
        return inventarioRepository.existsById(id);
    }
}