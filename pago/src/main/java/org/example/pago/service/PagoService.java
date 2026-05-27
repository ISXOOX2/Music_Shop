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

    //Metodo para actualizar
    public Pago actualizar(Integer id, PagoRequestDTO dto) {
        log.info("Iniciando actualización del pago con ID: {}", id);

        // Buscar si el pago existe
        Pago pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: No se encontró el pago con ID {}", id);
                    return new RuntimeException("Pago no encontrado");
                });

        //Si cambian el ID del pedido
        if (!pagoExistente.getPedidoId().equals(dto.getPedidoId())) {
            log.info("El ID del pedido cambió. Validando existencia del nuevo Pedido ID: {}", dto.getPedidoId());
            pedidoClient.obtenerPedidoPorId(dto.getPedidoId());
        }

        //Actualizar los datos
        pagoExistente.setPedidoId(dto.getPedidoId());
        pagoExistente.setMontoPagado(dto.getMontoPagado());
        pagoExistente.setMetodoPago(dto.getMetodoPago());

        Pago pagoActualizado = pagoRepository.save(pagoExistente);
        log.info("Pago ID {} actualizado exitosamente", id);

        return pagoActualizado;
    }

    //Metodo para verificar si existe
    public boolean existePorId(Integer id) {
        log.info("Verificando si existe el pago con ID: {}", id);
        return pagoRepository.existsById(id);
    }

    public void delete(Integer id) {
        log.info("Eliminando registro de pago con ID: {}", id);
        pagoRepository.deleteById(id);
    }
}