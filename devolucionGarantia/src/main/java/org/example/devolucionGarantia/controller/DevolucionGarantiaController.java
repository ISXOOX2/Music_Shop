package org.example.devolucionGarantia.controller;

import jakarta.validation.Valid;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/devoluciones")
public class DevolucionGarantiaController {

    @Autowired
    private DevolucionGarantiaService devolucionGarantiaService;

    @GetMapping
    public List<DevolucionGarantia> findAll(){
        return devolucionGarantiaService.findAll();
    }

    @GetMapping("/{id}")
    public DevolucionGarantia findById(@PathVariable Integer id){
        return devolucionGarantiaService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Devolución o garantía con id " + id + " no encontrada"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        if(!devolucionGarantiaService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Devolución o garantía con id " + id + " no encontrada");
        }
        devolucionGarantiaService.delete(id);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id){
        return devolucionGarantiaService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DevolucionGarantia create(@Valid @RequestBody DevolucionGarantia devolucionGarantia){
        return devolucionGarantiaService.save(devolucionGarantia);
    }

    @PutMapping("/{id}")
    public DevolucionGarantia update(@PathVariable Integer id,
                                     @Valid @RequestBody DevolucionGarantia devolucionGarantia){
        if(!devolucionGarantiaService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Devolución o garantía con id " + id + " no encontrada");
        }
        devolucionGarantia.setId(id); // Asegura actualizar registro correcto
        return devolucionGarantiaService.save(devolucionGarantia);
    }
}
