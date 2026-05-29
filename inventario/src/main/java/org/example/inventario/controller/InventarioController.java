package org.example.inventario.controller;

import jakarta.validation.Valid;
import org.example.inventario.dto.InventarioRequestDTO;
import org.example.inventario.model.Inventario;
import org.example.inventario.service.InventarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerStock(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.existePorId(id));
    }

    //Recibe InventarioRequestDTO
    @PostMapping
    public ResponseEntity<Inventario> crear(@Valid @RequestBody InventarioRequestDTO request) {
        return new ResponseEntity<>(inventarioService.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/reducir")
    public ResponseEntity<Inventario> reducirStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.reducirStock(id, cantidad));
    }

    @PutMapping("/{id}/aumentar")
    public ResponseEntity<Inventario> aumentarStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.aumentarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!inventarioService.existePorId(id)) {
            log.warn("Intento de eliminar un inventario inexistente con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario con id " + id + " no encontrado");
        }
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}