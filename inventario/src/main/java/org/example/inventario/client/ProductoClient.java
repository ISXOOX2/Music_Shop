package org.example.inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto")
public interface ProductoClient {

    @GetMapping("/api/productos/exists/{id}")
    boolean existeProductoPorId(@PathVariable("id") Integer id);
}