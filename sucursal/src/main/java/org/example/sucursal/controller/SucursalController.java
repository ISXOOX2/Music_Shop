package org.example.sucursal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.sucursal.dto.SucursalRequestDTO;
import org.example.sucursal.model.Sucursal;
import org.example.sucursal.service.SucursalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name="Sucursales", description="Administración de tiendas físicas")
public class SucursalController {

    private static final Logger log = LoggerFactory.getLogger(SucursalController.class);

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary="Obtener todas las sucursales", description="Retorna lista de todas las sucursales")
    @ApiResponse(responseCode="200", description="OK")
    public List<Sucursal> findAll(){
        return sucursalService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener sucursal por ID", description="Retorna una sucursal específica")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Sucursal findById(@Parameter(description="ID de la sucursal") @PathVariable Integer id){
        return sucursalService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Sucursal no encontrada: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear sucursal", description="Crea una nueva sucursal")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Sucursal create(@Valid @RequestBody SucursalRequestDTO sucursalDTO){
        return sucursalService.save(sucursalDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar sucursal", description="Actualiza una sucursal existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Sucursal> update(
            @Parameter(description="ID de la sucursal") @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO request) {
        Sucursal sucursalActualizada = sucursalService.update(id, request);
        return ResponseEntity.ok(sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar sucursal", description="Elimina una sucursal")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID de la sucursal") @PathVariable Integer id){
        if(!sucursalService.existsById(id)){
            log.warn("Intento eliminar sucursal inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada");
        }
        sucursalService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una sucursal")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID de la sucursal") @PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.existsById(id));
    }
}