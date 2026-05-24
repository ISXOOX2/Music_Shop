package org.example.devolucionGarantia.controller;

import jakarta.validation.Valid;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devoluciones-garantias")
public class DevolucionGarantiaController {

    private final DevolucionGarantiaService service;

    public DevolucionGarantiaController(DevolucionGarantiaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DevolucionGarantia> crearSolicitud(
            @Valid @RequestBody DevolucionGarantia solicitud,
            @RequestParam Integer idPedido,
            @RequestParam Integer idProducto,
            @RequestParam Integer cantidad) {

        DevolucionGarantia nuevaSol = service.registrarSolicitud(solicitud, idPedido, idProducto, cantidad);
        return new ResponseEntity<>(nuevaSol, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolucionGarantia> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
