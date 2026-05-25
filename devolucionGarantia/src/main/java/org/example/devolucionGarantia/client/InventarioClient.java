package org.example.devolucionGarantia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioClient {

    @PutMapping("/api/inventario/{id}/aumentar")
    ResponseEntity<Object> aumentarStock(@PathVariable("id") Integer id, @RequestParam("cantidad") Integer cantidad);
}