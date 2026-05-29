package org.example.bodega.controller;

import org.example.bodega.dto.BodegaRequestDTO;
import org.example.bodega.model.Bodega;
import org.example.bodega.service.BodegaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @GetMapping
    public List<Bodega> findAll() {
        return bodegaService.findAll();
    }

    @GetMapping("/{id}")
    public Bodega findById(@PathVariable Integer id) {
        return bodegaService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bodega con id " + id + " no encontrada"));
    }

    @GetMapping("/buscar/{nombre}")
    public List<Bodega> findByNombre(@PathVariable String nombre) {
        return bodegaService.findByNombre(nombre);
    }

    @GetMapping("/buscar/sucursal/{sucursalId}")
    public List<Bodega> findBySucursalId(@PathVariable Integer sucursalId) {
        return bodegaService.findBySucursalId(sucursalId);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return bodegaService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bodega create(@Valid @RequestBody BodegaRequestDTO dto) {
        return bodegaService.save(dto);
    }

    @PutMapping("/{id}")
    public Bodega update(@PathVariable Integer id,
                         @Valid @RequestBody BodegaRequestDTO dto) {
        if (!bodegaService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Bodega con id " + id + " no encontrada");
        }
        return bodegaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!bodegaService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Bodega con id " + id + " no encontrada");
        }
        bodegaService.delete(id);
    }
}
