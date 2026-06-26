package org.example.inventario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.inventario.dto.InventarioRequestDTO;
import org.example.inventario.model.Inventario;
import org.example.inventario.service.InventarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name="Inventarios", description="Gestión y control de stock disponible y reservado")
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary="Obtener todos los inventarios", description="Retorna lista de todos los inventarios registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de inventarios obtenida correctamente")
    public List<Inventario> listarTodos(){
        log.info("Obteniendo todos los inventarios");
        return inventarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener inventario por ID", description="Retorna un inventario específico")
    @ApiResponse(responseCode="200", description="OK - Inventario encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe con ese ID")
    public Inventario obtenerPorId(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id){

        log.info("Buscando inventario con ID: {}", id);
        return inventarioService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear inventario", description="Crea un nuevo inventario con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Inventario creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: productoId, bodegaId, cantidad")
    public Inventario crear(
            @Valid @RequestBody InventarioRequestDTO dto){

        log.info("Creando nuevo inventario para producto: {}", dto.getProductoId());
        Inventario inventario = inventarioService.crear(dto);
        log.info("Inventario creado con ID: {}", inventario.getId());
        return inventario;
    }

    @PutMapping("/reducir/{id}")
    @Operation(summary="Reducir stock", description="Reduce la cantidad disponible de stock")
    @ApiResponse(responseCode="200", description="OK - Stock reducido exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe")
    @ApiResponse(responseCode="400", description="OPERACIÓN INVÁLIDA - Stock insuficiente para reducir")
    public ResponseEntity<Inventario> reducirStock(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id,
            @Parameter(description="Cantidad a reducir", required=true)
            @RequestParam Integer cantidad) {

        log.info("Reduciendo stock en inventario ID: {} - Cantidad: {}", id, cantidad);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento reducir stock en inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        Inventario actualizado = inventarioService.reducirStock(id, cantidad);
        log.info("Stock reducido exitosamente en inventario ID: {}", id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/aumentar/{id}")
    @Operation(summary="Aumentar stock", description="Aumenta la cantidad disponible de stock")
    @ApiResponse(responseCode="200", description="OK - Stock aumentado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe")
    public ResponseEntity<Inventario> aumentarStock(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id,
            @Parameter(description="Cantidad a aumentar", required=true)
            @RequestParam Integer cantidad) {

        log.info("Aumentando stock en inventario ID: {} - Cantidad: {}", id, cantidad);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento aumentar stock en inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        Inventario actualizado = inventarioService.aumentarStock(id, cantidad);
        log.info("Stock aumentado exitosamente en inventario ID: {}", id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/reservar/{id}")
    @Operation(summary="Reservar stock", description="Mueve cantidad de disponible a reservada")
    @ApiResponse(responseCode="200", description="OK - Stock reservado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe")
    @ApiResponse(responseCode="400", description="OPERACIÓN INVÁLIDA - Stock disponible insuficiente")
    public ResponseEntity<Inventario> reservar(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id,
            @Parameter(description="Cantidad a reservar", required=true)
            @RequestParam Integer cantidad) {

        log.info("Reservando stock en inventario ID: {} - Cantidad: {}", id, cantidad);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento reservar stock en inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        Inventario actualizado = inventarioService.reservar(id, cantidad);
        log.info("Stock reservado exitosamente en inventario ID: {}", id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/revertir/{id}")
    @Operation(summary="Revertir reserva", description="Mueve cantidad de reservada de vuelta a disponible")
    @ApiResponse(responseCode="200", description="OK - Reserva revertida exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe")
    @ApiResponse(responseCode="400", description="OPERACIÓN INVÁLIDA - Cantidad a revertir excede lo reservado")
    public ResponseEntity<Inventario> revertirReserva(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id,
            @Parameter(description="Cantidad a revertir", required=true)
            @RequestParam Integer cantidad) {

        log.info("Revirtiendo reserva en inventario ID: {} - Cantidad: {}", id, cantidad);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento revertir reserva en inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        Inventario actualizado = inventarioService.revertirReserva(id, cantidad);
        log.info("Reserva revertida exitosamente en inventario ID: {}", id);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/verificar/{id}")
    @Operation(summary="Verificar stock disponible", description="Verifica si hay suficiente cantidad disponible")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe")
    public ResponseEntity<Boolean> verificarStock(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id,
            @Parameter(description="Cantidad solicitada", required=true)
            @RequestParam Integer cantidad) {

        log.info("Verificando stock en inventario ID: {} - Cantidad requerida: {}", id, cantidad);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento verificar stock en inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        boolean tieneStock = inventarioService.verificarStock(id, cantidad);
        return ResponseEntity.ok(tieneStock);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar inventario", description="Elimina un inventario del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Inventario eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Inventario no existe con ese ID")
    public void eliminar(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id){

        log.info("Eliminando inventario con ID: {}", id);

        if(!inventarioService.existePorId(id)){
            log.warn("Intento eliminar inventario inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Inventario con ID " + id + " no existe");
        }

        inventarioService.eliminar(id);
        log.info("Inventario eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un inventario con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existePorId(
            @Parameter(description="ID del inventario", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de inventario con ID: {}", id);
        boolean existe = inventarioService.existePorId(id);
        return ResponseEntity.ok(existe);
    }
}