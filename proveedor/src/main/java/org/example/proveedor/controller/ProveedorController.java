package org.example.proveedor.controller;

import org.example.proveedor.model.Proveedor;
import org.example.proveedor.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> findAll() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public Proveedor findById(@PathVariable Integer id) {
        return proveedorService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Proveedor con id " + id + " no encontrado"));
    }

    @GetMapping("/buscar/razonSocial")
    public List<Proveedor> findByRazonSocial(@RequestParam String razonSocial) {
        return proveedorService.findByRazonSocial(razonSocial);
    }

    @GetMapping("/buscar/rut")
    public Proveedor findByRut(@RequestParam String rut) {
        Proveedor proveedor = proveedorService.findByRut(rut);
        if (proveedor == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Proveedor con rut " + rut + " no encontrado");
        }
        return proveedor;
    }

    @GetMapping("/buscar/email")
    public Proveedor findByEmail(@RequestParam String email) {
        Proveedor proveedor = proveedorService.findByEmail(email);
        if (proveedor == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Proveedor con email " + email + " no encontrado");
        }
        return proveedor;
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return proveedorService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor create(@Valid @RequestBody Proveedor proveedor) {
        return proveedorService.save(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor update(@PathVariable Integer id,
                            @Valid @RequestBody Proveedor proveedor) {
        if (!proveedorService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Proveedor con id " + id + " no encontrado");
        }
        proveedor.setId(id);
        return proveedorService.save(proveedor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!proveedorService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Proveedor con id " + id + " no encontrado");
        }
        proveedorService.delete(id);
    }
}
