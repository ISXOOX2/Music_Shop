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

    // Instancia de SLF4J exigida por la rúbrica
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
    public void delete(Integer id){
        log.info("Eliminando pedido con ID: {}", id);
        pedidoRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return pedidoRepository.existsById(id);
    }
}