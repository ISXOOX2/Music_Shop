package org.example.devolucionGarantia.service;

import org.example.devolucionGarantia.client.InventarioClient;
import org.example.devolucionGarantia.client.PedidoClient;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.repository.DevolucionGarantiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public DevolucionGarantia registrarSolicitud(DevolucionGarantia solicitud,
                                                 Integer idPedido,
                                                 Integer idProducto,
                                                 Integer cantidad) {
        log.info("Iniciando registro de solicitud tipo '{}' para pedido ID: {}",
                solicitud.getTipoSolicitud(), idPedido);

        // Validación remota con microservicio pedido
        log.info("Validando existencia del pedido ID: {} en el microservicio de pedidos", idPedido);
        pedidoClient.obtenerPedidoPorId(idPedido);
        log.info("Pedido ID: {} validado correctamente", idPedido);

        // Lógica de negocio local
        solicitud.setEstadoResolucion("PROCESADA_Y_ACEPTADA");
        DevolucionGarantia guardada = repository.save(solicitud);
        log.info("Solicitud guardada exitosamente con ID: {}", guardada.getId());

        // Impacto remoto en inventario si es devolución
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

    public DevolucionGarantia obtenerPorId(Integer id) {
        log.info("Buscando solicitud de devolución/garantía con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Solicitud de devolución/garantía no encontrada con ID: {}", id);
                    return new RuntimeException("Registro de devolución/garantía no encontrado con ID: " + id);
                });
    }
}