package org.example.pago.controller;

import jakarta.validation.Valid;
import org.example.pago.model.Pago;
import org.example.pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> findAll(){
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public Pago findById(@PathVariable Integer id){
        return pagoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago con id " + id + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        if(!pagoService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pago con id " + id + " no encontrado");
        }
        pagoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id){
        return pagoService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Devuelve un estado 201 en la creación
    public Pago create(@Valid @RequestBody Pago pago){
        return pagoService.save(pago);
    }

    @PutMapping("/{id}")
    public Pago update(@PathVariable Integer id,
                       @Valid @RequestBody Pago pago){
        if(!pagoService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pago con id " + id + " no encontrado");
        }
        pago.setId(id); // Asegura que se este sobreescribiendo
        return pagoService.save(pago);
    }
}