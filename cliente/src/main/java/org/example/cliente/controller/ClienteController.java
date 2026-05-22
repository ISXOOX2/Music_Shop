package org.example.cliente.controller;

import org.example.cliente.model.Cliente;
import org.example.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Integer id) {
        return clienteService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente con id " + id + " no encontrado"));
    }

    @GetMapping("/buscar/nombre")
    public List<Cliente> findByNombre(@RequestParam String nombre) {
        return clienteService.findByNombre(nombre);
    }

    @GetMapping("/buscar/rut")
    public Cliente findByRut(@RequestParam String rut) {
        Cliente cliente = clienteService.findByRut(rut);
        if (cliente == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente con rut " + rut + " no encontrado");
        }
        return cliente;
    }

    @GetMapping("/buscar/email")
    public Cliente findByEmail(@RequestParam String email) {
        Cliente cliente = clienteService.findByEmail(email);
        if (cliente == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente con email " + email + " no encontrado");
        }
        return cliente;
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id) {
        return clienteService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@Valid @RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Integer id,
                          @Valid @RequestBody Cliente cliente) {
        if (!clienteService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente con id " + id + " no encontrado");
        }
        cliente.setId(id);
        return clienteService.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!clienteService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente con id " + id + " no encontrado");
        }
        clienteService.delete(id);
    }
}
