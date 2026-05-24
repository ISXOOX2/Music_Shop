package org.example.devolucionGarantia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido")
public interface PedidoClient {

    @GetMapping("/api/pedidos/{id}")
    ResponseEntity<Object> obtenerPedidoPorId(@PathVariable("id") Integer id);
}
