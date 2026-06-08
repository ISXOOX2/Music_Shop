package org.example.devolucionGarantia.service;

import org.example.devolucionGarantia.client.InventarioClient;
import org.example.devolucionGarantia.client.PedidoClient;
import org.example.devolucionGarantia.dto.DevolucionGarantiaDTO;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.repository.DevolucionGarantiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DevolucionGarantiaService {

    private static final Logger log = LoggerFactory.getLogger(DevolucionGarantiaService.class);

    private final DevolucionGarantiaRepository repository;
    private final PedidoClient pedidoClient;
    private final InventarioClient inventarioClient;

    public DevolucionGarantiaService(DevolucionGarantiaRepository repository,
                                     PedidoClient pedidoClient,
                                     InventarioClient inventarioClient) {
        this.repository = repository;
        this.pedidoClient = pedidoClient;
        this.inventarioClient = inventarioClient;
    }

    public List<DevolucionGarantia> listarTodos() {
        log.info("Obteniendo todas las solicitudes de devolución o garantía");
        return repository.findAll();
    }

    public DevolucionGarantia obtenerPorId(Integer id) {
        log.info("Buscando solicitud de devolución/garantía con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado con ID: " + id));
    }

    //Procesa el DTO transaccional completo
    @Transactional
    public DevolucionGarantia registrarSolicitud(DevolucionGarantiaDTO dto) {
        log.info("Iniciando registro de solicitud tipo '{}' para pedido ID: {}", dto.getTipoSolicitud(), dto.getIdPedido());

        log.info("Validando existencia del pedido ID: {} vía Feign", dto.getIdPedido());
        try {
            pedidoClient.obtenerPedidoPorId(dto.getIdPedido());
        } catch (Exception e) {
            log.error("Error al validar pedido: El microservicio de Pedidos no responde o el ID no existe.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido especificado no existe o no pudo ser validado.");
        }


        DevolucionGarantia solicitud = new DevolucionGarantia();
        solicitud.setTipoSolicitud(dto.getTipoSolicitud());
        solicitud.setMotivo(dto.getMotivo());
        solicitud.setEstadoResolucion("PROCESADA_Y_ACEPTADA");

        DevolucionGarantia guardada = repository.save(solicitud);
        log.info("Solicitud guardada en base de datos con ID: {}", guardada.getId());

        // Regla de negocio de inventario
        if ("DEVOLUCION".equalsIgnoreCase(dto.getTipoSolicitud())) {
            log.info("Tipo DEVOLUCION detectado. Aumentando stock en inventario ID: {} en cantidad: {}", dto.getIdInventario(), dto.getCantidad());
            try {
                inventarioClient.aumentarStock(dto.getIdInventario(), dto.getCantidad());
                log.info("Stock actualizado correctamente vía Feign");
            } catch (Exception e) {
                log.error("Error crítico: No se pudo actualizar el stock en el inventario.");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Solicitud guardada pero falló la actualización del inventario.");
            }
        } else {
            log.info("Tipo GARANTIA detectado. No se altera el stock del inventario global");
        }

        return guardada;
    }

    public DevolucionGarantia actualizar(Integer id, DevolucionGarantia solicitudActualizada) {
        log.info("Iniciando actualización de solicitud con ID: {}", id);

        DevolucionGarantia existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        existente.setTipoSolicitud(solicitudActualizada.getTipoSolicitud());
        existente.setEstadoResolucion(solicitudActualizada.getEstadoResolucion());
        existente.setMotivo(solicitudActualizada.getMotivo());

        return repository.save(existente);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando solicitud con ID: {}", id);
        repository.deleteById(id);
    }

    public boolean existePorId(Integer id) {
        return repository.existsById(id);
    }
}