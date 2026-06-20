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
        try {
            log.info("Obteniendo todos los registros de pagos");
            return pagoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de pagos: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de pagos");
        }
    }

    public Optional<Pago> findById(Integer id) {
        try {
            log.info("Buscando pago con ID: {}", id);
            Optional<Pago> resultado = pagoRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún pago con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar pago con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el pago con ID: " + id);
        }
    }

    public Pago save(PagoRequestDTO dto) {
        try {
            log.info("Iniciando procesamiento de pago para el Pedido ID: {}", dto.getPedidoId());

            log.info("Consultando existencia del pedido ID: {} en el microservicio de pedidos", dto.getPedidoId());
            pedidoClient.obtenerPedidoPorId(dto.getPedidoId());
            log.info("Pedido ID: {} verificado correctamente. Procediendo a registrar el pago.", dto.getPedidoId());

            Pago pago = new Pago();
            pago.setPedidoId(dto.getPedidoId());
            pago.setMontoPagado(dto.getMontoPagado());
            pago.setMetodoPago(dto.getMetodoPago());
            pago.setFechaPago(LocalDateTime.now());

            Pago guardado = pagoRepository.save(pago);
            log.info("Pago registrado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (RuntimeException e) {
            log.error("Error de negocio al procesar pago para pedido ID {}: {}", dto.getPedidoId(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al procesar pago para pedido ID {}: {}", dto.getPedidoId(), e.getMessage());
            throw new RuntimeException("No se pudo registrar el pago para el pedido ID: " + dto.getPedidoId());
        }
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
        try {
            log.warn("Eliminando registro de pago con ID: {}", id);
            pagoRepository.deleteById(id);
            log.info("Pago con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar pago con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el pago con ID: " + id);
        }
    }
}