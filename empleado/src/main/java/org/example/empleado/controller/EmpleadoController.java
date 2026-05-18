package org.example.empleado.controller;

import org.example.empleado.model.Empleado;
import org.example.empleado.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> findAll() {
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    public Empleado findById(@PathVariable Integer id) {
        return empleadoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empleado con id " + id + " no encontrado"));
    }

    @GetMapping("/buscar/nombre")
    public List<Empleado> findByNombre(@RequestParam String nombre) {
        return empleadoService.findByNombre(nombre);
    }

    @GetMapping("/buscar/sucursal")
    public List<Empleado> findBySucursalId(@RequestParam Integer sucursalId) {
        return empleadoService.findBySucursalId(sucursalId);
    }

    @GetMapping("/buscar/cedula")
    public Empleado findByCedula(@RequestParam String cedula) {
        Empleado empleado = empleadoService.findByCedula(cedula);
        if (empleado == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empleado con cédula " + cedula + " no encontrado");
        }
        return empleado;
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return empleadoService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado create(@Valid @RequestBody Empleado empleado) {
        return empleadoService.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado update(@PathVariable Integer id,
                           @Valid @RequestBody Empleado empleado) {
        if (!empleadoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empleado con id " + id + " no encontrado");
        }
        empleado.setId(id);
        return empleadoService.save(empleado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!empleadoService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empleado con id " + id + " no encontrado");
        }
        empleadoService.delete(id);
    }
}