package org.example.inventario.controller;

import jakarta.validation.Valid;
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

    //Para listar todos (GET)
    @GetMapping
    public ResponseEntity<List<Inventario>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    //Buscar stock de un producto por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerStock(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    //Para verificar si existe (GET)
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.existePorId(id));
    }

    //Para crear (POST)
    @PostMapping
    public ResponseEntity<Inventario> crear(@Valid @RequestBody Inventario request) {
        return new ResponseEntity<>(inventarioService.crear(request), HttpStatus.CREATED);
    }

    //Reducir el stock de un producto (PUT)
    @PutMapping("/{id}/reducir")
    public ResponseEntity<Inventario> reducirStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.reducirStock(id, cantidad));
    }

    //Aumentar el stock de un producto en caso de devoluciones(PUT)
    @PutMapping("/{id}/aumentar")
    public ResponseEntity<Inventario> aumentarStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.aumentarStock(id, cantidad));
    }

    //Para eliminar (DELETE)
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