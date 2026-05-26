package org.example.sucursal.controller;

import org.example.sucursal.model.Sucursal;
import org.example.sucursal.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public List<Sucursal> findAll() {
        return sucursalService.findAll();
    }

    @GetMapping("/{id}")
    public Sucursal findById(@PathVariable Integer id) {
        return sucursalService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sucursal con id " + id + " no encontrada"));
    }

    @GetMapping("/buscar/{nombre}")
    public List<Sucursal> findByNombre(@PathVariable String nombre) {
        return sucursalService.findByNombre(nombre);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return sucursalService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sucursal create(@Valid @RequestBody Sucursal sucursal) {
        return sucursalService.save(sucursal);
    }

    @PutMapping("/{id}")
    public Sucursal update(@PathVariable Integer id,
                           @Valid @RequestBody Sucursal sucursal) {
        if (!sucursalService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Sucursal con id " + id + " no encontrada");
        }
        sucursal.setId(id);
        return sucursalService.save(sucursal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!sucursalService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Sucursal con id " + id + " no encontrada");
        }
        sucursalService.delete(id);
    }
}