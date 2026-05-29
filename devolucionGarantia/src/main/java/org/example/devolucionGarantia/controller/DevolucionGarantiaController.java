package org.example.devolucionGarantia.controller;

import jakarta.validation.Valid;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/devoluciones-garantias")
public class DevolucionGarantiaController {

    private static final Logger log = LoggerFactory.getLogger(DevolucionGarantiaController.class);

    private final DevolucionGarantiaService service;

    public DevolucionGarantiaController(DevolucionGarantiaService service) {
        this.service = service;
    }

    // NUEVO: Listar todos (GET)
    @GetMapping
    public ResponseEntity<List<DevolucionGarantia>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolucionGarantia> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // NUEVO: Verificar si existe
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Integer id) {
        return ResponseEntity.ok(service.existePorId(id));
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

    // NUEVO: Actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<DevolucionGarantia> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DevolucionGarantia request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    // NUEVO: Eliminar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!service.existePorId(id)) {
            log.warn("Intento de eliminar una solicitud inexistente con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud con id " + id + " no encontrada");
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
