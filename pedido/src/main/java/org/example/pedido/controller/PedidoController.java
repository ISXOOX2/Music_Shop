package org.example.pedido.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Pedidos", description="Gestión de pedidos y órdenes")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary="Obtener todos los pedidos", description="Retorna lista de todos los pedidos")
    @ApiResponse(responseCode="200", description="OK")
    public List<Pedido> findAll(){
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener pedido por ID", description="Retorna un pedido específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Pedido findById(@Parameter(description="ID del pedido") @PathVariable Integer id){
        return pedidoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear pedido", description="Crea un nuevo pedido")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Pedido create(@Valid @RequestBody PedidoRequestDTO pedidoDTO){
        return pedidoService.save(pedidoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar pedido", description="Actualiza un pedido existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description="ID del pedido") @PathVariable Integer id,
            @Valid @RequestBody PedidoRequestDTO request) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, request);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar pedido", description="Elimina un pedido")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public void delete(@Parameter(description="ID del pedido") @PathVariable Integer id){
        if(!pedidoService.existsById(id)){
            log.warn("Intento eliminar pedido inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
        }
        pedidoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un pedido")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del pedido") @PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.existsById(id));
    }
}