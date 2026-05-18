package org.example.producto.controller;

import org.example.producto.model.Producto;
import org.example.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> findAll() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto findById(@PathVariable Integer id) {
        return productoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto con id " + id + " no encontrado"));
    }

    @GetMapping("/buscar/nombre")
    public List<Producto> findByNombre(@RequestParam String nombre) {
        return productoService.findByNombre(nombre);
    }

    @GetMapping("/buscar/proveedor")
    public List<Producto> findByProveedorId(@RequestParam Integer proveedorId) {
        return productoService.findByProveedorId(proveedorId);
    }

    @GetMapping("/buscar/activos")
    public List<Producto> findByActivo(@RequestParam Boolean activo) {
        return productoService.findByActivo(activo);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return productoService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto create(@Valid @RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Integer id,
                           @Valid @RequestBody Producto producto) {
        if (!productoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Producto con id " + id + " no encontrado");
        }
        producto.setId(id);
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!productoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Producto con id " + id + " no encontrado");
        }
        productoService.delete(id);
    }
}
