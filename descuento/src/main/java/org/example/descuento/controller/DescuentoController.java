package org.example.descuento.controller;

import org.example.descuento.model.Descuento;
import org.example.descuento.service.DescuentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    public List<Descuento> findAll() {
        return descuentoService.findAll();
    }

    @GetMapping("/{id}")
    public Descuento findById(@PathVariable Integer id) {
        return descuentoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Descuento con id " + id + " no encontrado"));
    }

    @GetMapping("/buscar/codigo")
    public Descuento findByCodigo(@RequestParam String codigo) {
        Descuento descuento = descuentoService.findByCodigo(codigo);
        if (descuento == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Descuento con código " + codigo + " no encontrado");
        }
        return descuento;
    }

    @GetMapping("/buscar/porcentaje")
    public List<Descuento> findByPorcentaje(@RequestParam Double porcentaje) {
        return descuentoService.findByPorcentaje(porcentaje);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return descuentoService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Descuento create(@Valid @RequestBody Descuento descuento) {
        return descuentoService.save(descuento);
    }

    @PutMapping("/{id}")
    public Descuento update(@PathVariable Integer id,
                            @Valid @RequestBody Descuento descuento) {
        if (!descuentoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Descuento con id " + id + " no encontrado");
        }
        descuento.setId(id);
        return descuentoService.save(descuento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!descuentoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Descuento con id " + id + " no encontrado");
        }
        descuentoService.delete(id);
    }
}
