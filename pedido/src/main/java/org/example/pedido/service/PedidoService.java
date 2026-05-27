package org.example.pedido.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pedido.dto.PedidoRequestDTO;
import org.example.pedido.model.Pedido;
import org.example.pedido.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll(){
        log.info("Obteniendo la lista de todos los pedidos");
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Integer id){
        log.info("Buscando pedido con ID: {}", id);
        return pedidoRepository.findById(id);
    }

    public Pedido save(PedidoRequestDTO dto){
        log.info("Iniciando la creación de un nuevo pedido para el cliente ID: {}", dto.getClienteId());

        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setTotalFinal(dto.getTotalFinal());
        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setFechaEmision(LocalDateTime.now());

        Pedido guardado = pedidoRepository.save(pedido);

        log.info("Pedido creado exitosamente en la base de datos con ID: {}", guardado.getId());
        return guardado;
    }

    //Método para actualizar
    public Pedido actualizar(Integer id, PedidoRequestDTO dto) {
        log.info("Iniciando actualización del pedido con ID: {}", id);

        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: No se encontró el pedido con ID {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
                });

        pedidoExistente.setClienteId(dto.getClienteId());
        pedidoExistente.setTotalFinal(dto.getTotalFinal());
        pedidoExistente.setEstadoPedido(dto.getEstadoPedido());


        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);
        log.info("Pedido ID {} actualizado exitosamente a estado: {}", id, dto.getEstadoPedido());

        return pedidoActualizado;
    }

    public void delete(Integer id){
        log.info("Eliminando pedido con ID: {}", id);
        pedidoRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        log.info("Verificando existencia del pedido con ID: {}", id);
        return pedidoRepository.existsById(id);
    }
}