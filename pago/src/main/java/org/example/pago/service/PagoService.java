package org.example.pago.service;

import org.example.pago.client.PedidoClient;
import org.example.pago.dto.PagoRequestDTO;
import org.example.pago.model.Pago;
import org.example.pago.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoClient pedidoClient;

    public List<Pago> findAll() {
        log.info("Obteniendo todos los registros de pagos");
        return pagoRepository.findAll();
    }

    public Optional<Pago> findById(Integer id) {
        log.info("Buscando pago con ID: {}", id);
        return pagoRepository.findById(id);
    }

    public Pago save(PagoRequestDTO dto) {
        log.info("Iniciando procesamiento de pago para el Pedido ID: {}", dto.getPedidoId());

        //Comunicacion entre mircoservicios
        log.info("Consultando al microservicio 'pedido-service' la existencia del pedido ID: {}", dto.getPedidoId());
        pedidoClient.obtenerPedidoPorId(dto.getPedidoId());
        log.info("Verificación exitosa: El pedido ID {} existe. Procediendo a registrar el pago.", dto.getPedidoId());

        //Creacion del pago
        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMontoPagado(dto.getMontoPagado());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setFechaPago(LocalDateTime.now());

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago registrado exitosamente en la base de datos con ID: {}", guardado.getId());

        return guardado;
    }

    public void delete(Integer id) {
        log.info("Eliminando registro de pago con ID: {}", id);
        pagoRepository.deleteById(id);
    }
}