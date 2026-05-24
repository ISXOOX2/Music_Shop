package org.example.inventario.controller;

import org.example.inventario.model.Inventario;
import org.example.inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // GET: Buscar stock de un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerStock(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    // PUT: Reducir el stock de un producto
    @PutMapping("/{id}/reducir")
    public ResponseEntity<Inventario> reducirStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.reducirStock(id, cantidad));
    }

    // PUT: Aumentar el stock de un producto (Para devoluciones)
    @PutMapping("/{id}/aumentar")
    public ResponseEntity<Inventario> aumentarStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.aumentarStock(id, cantidad));
    }
}