package org.example.pago.client;

import org.example.pago.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "pedido")
public interface PedidoClient {

    @GetMapping("/api/pedidos/{id}")
    PedidoDTO obtenerPedidoPorId(@PathVariable Integer id);
}