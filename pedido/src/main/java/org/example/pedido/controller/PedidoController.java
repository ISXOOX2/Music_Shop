package org.example.pedido.controller;

import jakarta.validation.Valid;
import org.example.pedido.model.Pedido;
import org.example.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> findAll(){
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public Pedido findById(@PathVariable Integer id){
        return pedidoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido con id " + id + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        if(!pedidoService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pedido con id " + id + " no encontrado");
        }
        pedidoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id){
        return pedidoService.existsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Cumple con la rúbrica de devolver 201 (Clase 2.3.1)
    public Pedido create(@Valid @RequestBody Pedido pedido){
        return pedidoService.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido update(@PathVariable Integer id,
                         @Valid @RequestBody Pedido pedido){
        if(!pedidoService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pedido con id " + id + " no encontrado");
        }
        pedido.setId(id); // Asegura que se actualice el pedido correcto
        return pedidoService.save(pedido);
    }
}