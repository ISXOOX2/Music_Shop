package org.example.devolucionGarantia.controller;

import jakarta.validation.Valid;
import org.example.devolucionGarantia.dto.DevolucionGarantiaDTO;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devoluciones-garantias")
public class DevolucionGarantiaController {

    private final DevolucionGarantiaService service;

    public DevolucionGarantiaController(DevolucionGarantiaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DevolucionGarantia>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolucionGarantia> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Integer id) {
        return ResponseEntity.ok(service.existePorId(id));
    }


    @PostMapping
    public ResponseEntity<DevolucionGarantia> crearSolicitud(@Valid @RequestBody DevolucionGarantiaDTO dto) {
        DevolucionGarantia nuevaSol = service.registrarSolicitud(dto);
        return new ResponseEntity<>(nuevaSol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevolucionGarantia> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DevolucionGarantia request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
