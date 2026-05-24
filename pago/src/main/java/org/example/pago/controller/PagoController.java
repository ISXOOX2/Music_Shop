package org.example.pago.controller;

import jakarta.validation.Valid;
import org.example.pago.dto.PagoRequestDTO;
import org.example.pago.model.Pago;
import org.example.pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> getAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getById(@PathVariable Integer id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pago> create(@Valid @RequestBody PagoRequestDTO dto) {
        Pago nuevoPago = pagoService.save(dto);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }
}