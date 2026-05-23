package org.example.inventario.controller;

import jakarta.validation.Valid;
import org.example.inventario.model.Inventario;
import org.example.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> findAll(){
        return inventarioService.findAll();
    }

    @GetMapping("/{id}")
    public Inventario findById(@PathVariable Integer id){
        return inventarioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Registro de inventario con id " + id + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        if(!inventarioService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Registro de inventario con id " + id + " no encontrado");
        }
        inventarioService.delete(id);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id){
        return inventarioService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Código 201 requerido por el profesor
    public Inventario create(@Valid @RequestBody Inventario inventario){
        return inventarioService.save(inventario);
    }

    @PutMapping("/{id}")
    public Inventario update(@PathVariable Integer id,
                             @Valid @RequestBody Inventario inventario){
        if(!inventarioService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Registro de inventario con id " + id + " no encontrado");
        }
        inventario.setId(id); // Aseguramos que se actualice el correcto
        return inventarioService.save(inventario);
    }
}