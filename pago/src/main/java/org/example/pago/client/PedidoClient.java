package org.example.pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

// El name debe ser EXACTAMENTE el mismo nombre con el que el microservicio de pedidos se registra en Eureka
@FeignClient(name = "pedido-service")
public interface PedidoClient {

    // Este método debe apuntar EXACTAMENTE al endpoint de tu PedidoController que busca por ID
    @GetMapping("/api/pedidos/{id}")
    ResponseEntity<Object> obtenerPedidoPorId(@PathVariable("id") Integer id);
}