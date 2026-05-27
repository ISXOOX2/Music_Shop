package org.example.devolucionGarantia.service;

import org.example.devolucionGarantia.client.InventarioClient;
import org.example.devolucionGarantia.client.PedidoClient;
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

    // NUEVO: Listar todos
    public List<DevolucionGarantia> listarTodos() {
        log.info("Obteniendo todas las solicitudes de devolución o garantía");
        return repository.findAll();
    }

    public DevolucionGarantia obtenerPorId(Integer id) {
        log.info("Buscando solicitud de devolución/garantía con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Solicitud de devolución/garantía no encontrada con ID: {}", id);

                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado con ID: " + id);
                });
    }

    @Transactional
    public DevolucionGarantia registrarSolicitud(DevolucionGarantia solicitud,
                                                 Integer idPedido,
                                                 Integer idProducto,
                                                 Integer cantidad) {
        log.info("Iniciando registro de solicitud tipo '{}' para pedido ID: {}",
                solicitud.getTipoSolicitud(), idPedido);

        log.info("Validando existencia del pedido ID: {} en el microservicio de pedidos", idPedido);
        pedidoClient.obtenerPedidoPorId(idPedido);
        log.info("Pedido ID: {} validado correctamente", idPedido);

        solicitud.setEstadoResolucion("PROCESADA_Y_ACEPTADA");
        DevolucionGarantia guardada = repository.save(solicitud);
        log.info("Solicitud guardada exitosamente con ID: {}", guardada.getId());

        if ("DEVOLUCION".equalsIgnoreCase(solicitud.getTipoSolicitud())) {
            log.info("Tipo DEVOLUCION detectado. Aumentando stock del producto ID: {} en cantidad: {}",
                    idProducto, cantidad);
            inventarioClient.aumentarStock(idProducto, cantidad);
            log.info("Stock actualizado correctamente en el microservicio de inventario");
        } else {
            log.info("Tipo GARANTIA detectado. No se modifica el stock del inventario");
        }

        return guardada;
    }

    //Actualizar
    public DevolucionGarantia actualizar(Integer id, DevolucionGarantia solicitudActualizada) {
        log.info("Iniciando actualización de solicitud con ID: {}", id);

        DevolucionGarantia existente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: No se encontró la solicitud ID {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada");
                });

        existente.setTipoSolicitud(solicitudActualizada.getTipoSolicitud());
        existente.setEstadoResolucion(solicitudActualizada.getEstadoResolucion());

        DevolucionGarantia guardada = repository.save(existente);
        log.info("Solicitud ID {} actualizada exitosamente", id);

        return guardada;
    }

    //Eliminar
    public void eliminar(Integer id) {
        log.info("Eliminando solicitud con ID: {}", id);
        repository.deleteById(id);
    }

    //Verificar existencia
    public boolean existePorId(Integer id) {
        log.info("Verificando existencia de solicitud ID: {}", id);
        return repository.existsById(id);
    }
}