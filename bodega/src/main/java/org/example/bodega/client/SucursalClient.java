package org.example.bodega.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sucursal")
public interface SucursalClient {

    @GetMapping("/api/sucursales/exists/{id}")
    boolean existeSucursalPorId(@PathVariable("id") Integer id);
}
