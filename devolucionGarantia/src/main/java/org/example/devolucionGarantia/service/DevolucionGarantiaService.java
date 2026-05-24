package org.example.devolucionGarantia.service;

import org.example.devolucionGarantia.client.InventarioClient;
import org.example.devolucionGarantia.client.PedidoClient;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.repository.DevolucionGarantiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DevolucionGarantiaService {

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
    public DevolucionGarantia registrarSolicitud(DevolucionGarantia solicitud, Integer idPedido, Integer idProducto, Integer cantidad) {

        // Validación remota
        pedidoClient.obtenerPedidoPorId(idPedido);

        //Lógica de negocio local
        solicitud.setEstadoResolucion("PROCESADA_Y_ACEPTADA");
        DevolucionGarantia guardada = repository.save(solicitud);

        //Impacto remoto
        if ("DEVOLUCION".equalsIgnoreCase(solicitud.getTipoSolicitud())) {
            inventarioClient.aumentarStock(idProducto, cantidad);
        }

        return guardada;
    }

    public DevolucionGarantia obtenerPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de devolución/garantía no encontrado con ID: " + id));
    }
}