package org.example.pedido.service;

import org.example.pedido.model.Pedido;
import org.example.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Integer id){
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido){
        // Mantenemos esta pequeña ayuda para que la fecha se ponga sola si viene nula
        if (pedido.getFechaEmision() == null) {
            pedido.setFechaEmision(LocalDateTime.now());
        }
        return pedidoRepository.save(pedido);
    }

    public void delete(Integer id){
        pedidoRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return pedidoRepository.existsById(id);
    }
}