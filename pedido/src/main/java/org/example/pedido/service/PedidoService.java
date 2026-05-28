package org.example.pedido.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pedido.dto.PedidoRequestDTO;
import org.example.pedido.model.Pedido;
import org.example.pedido.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        try {
            log.info("Obteniendo la lista de todos los pedidos");
            return pedidoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de pedidos: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de pedidos");
        }
    }

    public Optional<Pedido> findById(Integer id) {
        try {
            log.info("Buscando pedido con ID: {}", id);
            Optional<Pedido> resultado = pedidoRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún pedido con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar pedido con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el pedido con ID: " + id);
        }
    }

    public Pedido save(PedidoRequestDTO dto) {
        try {
            log.info("Iniciando la creación de un nuevo pedido para el cliente ID: {}", dto.getClienteId());

            Pedido pedido = new Pedido();
            pedido.setClienteId(dto.getClienteId());
            pedido.setTotalFinal(dto.getTotalFinal());
            pedido.setEstadoPedido(dto.getEstadoPedido());
            pedido.setFechaEmision(LocalDateTime.now());

            Pedido guardado = pedidoRepository.save(pedido);
            log.info("Pedido creado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al crear pedido para cliente ID {}: {}", dto.getClienteId(), e.getMessage());
            throw new RuntimeException("No se pudo crear el pedido para el cliente ID: " + dto.getClienteId());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando pedido con ID: {}", id);
            pedidoRepository.deleteById(id);
            log.info("Pedido con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar pedido con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el pedido con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return pedidoRepository.existsById(id);
    }
}