package org.example.pedido.controller;

import jakarta.validation.Valid;
import org.example.pedido.dto.PedidoRequestDTO;
import org.example.pedido.model.Pedido;
import org.example.pedido.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> findAll(){
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public Pedido findById(@PathVariable Integer id){
        return pedidoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de buscar un pedido inexistente con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido con id " + id + " no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido create(@Valid @RequestBody PedidoRequestDTO pedidoDTO){
        return pedidoService.save(pedidoDTO);
    }

    //Para actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @Valid @RequestBody PedidoRequestDTO request) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, request);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        if(!pedidoService.existsById(id)){
            log.warn("Intento de eliminar un pedido inexistente con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido con id " + id + " no encontrado");
        }
        pedidoService.delete(id);
    }

    //Para verificar si existe (GET /exists/{id})
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.existsById(id));
    }
}