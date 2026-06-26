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
    @Operation(summary="Obtener todos los pedidos", description="Retorna lista de todos los pedidos registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de pedidos obtenida correctamente")
    public List<Pedido> findAll(){
        log.info("Obteniendo todos los pedidos");
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener pedido por ID", description="Retorna un pedido específico")
    @ApiResponse(responseCode="200", description="OK - Pedido encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pedido no existe con ese ID")
    public Pedido findById(
            @Parameter(description="ID del pedido", required=true)
            @PathVariable Integer id){

        log.info("Buscando pedido con ID: {}", id);
        return pedidoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Pedido con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear pedido", description="Crea un nuevo pedido con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Pedido creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: clienteId, fechaPedido")
    public Pedido create(
            @Valid @RequestBody PedidoRequestDTO pedidoDTO){

        log.info("Creando nuevo pedido para cliente: {}", pedidoDTO.getClienteId());
        Pedido pedido = pedidoService.save(pedidoDTO);
        log.info("Pedido creado con ID: {}", pedido.getId());
        return pedido;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar pedido", description="Actualiza un pedido existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pedido no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description="ID del pedido", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody PedidoRequestDTO request) {

        log.info("Actualizando pedido con ID: {}", id);

        if(!pedidoService.existsById(id)){
            log.warn("Intento actualizar pedido inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Pedido con ID " + id + " no existe");
        }

        Pedido pedidoActualizado = pedidoService.actualizar(id, request);
        log.info("Pedido actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar pedido", description="Elimina un pedido del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Pedido eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pedido no existe con ese ID")
    public void delete(
            @Parameter(description="ID del pedido", required=true)
            @PathVariable Integer id){

        log.info("Eliminando pedido con ID: {}", id);

        if(!pedidoService.existsById(id)){
            log.warn("Intento eliminar pedido inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Pedido con ID " + id + " no existe");
        }

        pedidoService.delete(id);
        log.info("Pedido eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un pedido con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del pedido", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de pedido con ID: {}", id);
        boolean existe = pedidoService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}