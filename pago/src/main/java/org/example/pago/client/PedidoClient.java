package org.example.pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;


@FeignClient(name = "pedido-service")
public interface PedidoClient {

    @GetMapping("/api/pedidos/{id}")
    ResponseEntity<Object> obtenerPedidoPorId(@PathVariable("id") Integer id);
}