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

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name="Inventarios", description="Gestión y control de stock disponible y reservado")
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary="Obtener todos los inventarios", description="Retorna lista de todos los inventarios")
    @ApiResponse(responseCode="200", description="OK")
    public List<Inventario> listarTodos(){
        return inventarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener inventario por ID", description="Retorna un inventario específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Inventario obtenerPorId(@Parameter(description="ID del inventario") @PathVariable Integer id){
        return inventarioService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear inventario", description="Crea un nuevo inventario")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Inventario crear(@Valid @RequestBody InventarioRequestDTO dto){
        return inventarioService.crear(dto);
    }

    @PutMapping("/reducir/{id}")
    @Operation(summary="Reducir stock", description="Reduce la cantidad disponible de stock")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    public ResponseEntity<Inventario> reducirStock(
            @Parameter(description="ID del inventario") @PathVariable Integer id,
            @Parameter(description="Cantidad a reducir") @RequestParam Integer cantidad) {
        Inventario actualizado = inventarioService.reducirStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/aumentar/{id}")
    @Operation(summary="Aumentar stock", description="Aumenta la cantidad disponible de stock")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    public ResponseEntity<Inventario> aumentarStock(
            @Parameter(description="ID del inventario") @PathVariable Integer id,
            @Parameter(description="Cantidad a aumentar") @RequestParam Integer cantidad) {
        Inventario actualizado = inventarioService.aumentarStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/reservar/{id}")
    @Operation(summary="Reservar stock", description="Mueve cantidad de disponible a reservada")
    @ApiResponse(responseCode="200", description="Reservado exitosamente")
    @ApiResponse(responseCode="400", description="Stock disponible insuficiente")
    public ResponseEntity<Inventario> reservar(
            @Parameter(description="ID del inventario") @PathVariable Integer id,
            @Parameter(description="Cantidad a reservar") @RequestParam Integer cantidad) {
        Inventario actualizado = inventarioService.reservar(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/revertir/{id}")
    @Operation(summary="Revertir reserva", description="Mueve cantidad de reservada de vuelta a disponible")
    @ApiResponse(responseCode="200", description="Revertido exitosamente")
    @ApiResponse(responseCode="400", description="Cantidad a revertir excede lo reservado")
    public ResponseEntity<Inventario> revertirReserva(
            @Parameter(description="ID del inventario") @PathVariable Integer id,
            @Parameter(description="Cantidad a revertir") @RequestParam Integer cantidad) {
        Inventario actualizado = inventarioService.revertirReserva(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/verificar/{id}")
    @Operation(summary="Verificar stock disponible", description="Verifica si hay suficiente cantidadDisponible")
    @ApiResponse(responseCode="200", description="OK")
    public ResponseEntity<Boolean> verificarStock(
            @Parameter(description="ID del inventario") @PathVariable Integer id,
            @Parameter(description="Cantidad solicitada") @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.verificarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar inventario", description="Elimina un inventario")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void eliminar(@Parameter(description="ID del inventario") @PathVariable Integer id){
        inventarioService.eliminar(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un inventario")
    public ResponseEntity<Boolean> existePorId(@Parameter(description="ID del inventario") @PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.existePorId(id));
    }
}