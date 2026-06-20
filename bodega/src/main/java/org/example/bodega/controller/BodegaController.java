package org.example.bodega.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.bodega.dto.BodegaRequestDTO;
import org.example.bodega.model.Bodega;
import org.example.bodega.service.BodegaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@Tag(name="Bodegas", description="Control de logística e inventario")
public class BodegaController {

    private static final Logger log = LoggerFactory.getLogger(BodegaController.class);

    @Autowired
    private BodegaService bodegaService;

    @GetMapping
    @Operation(summary="Obtener todas las bodegas", description="Retorna lista de todas las bodegas")
    @ApiResponse(responseCode="200", description="OK")
    public List<Bodega> findAll(){
        return bodegaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener bodega por ID", description="Retorna una bodega específica")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Bodega findById(@Parameter(description="ID de la bodega") @PathVariable Integer id){
        return bodegaService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bodega no encontrada: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear bodega", description="Crea una nueva bodega")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Bodega create(@Valid @RequestBody BodegaRequestDTO bodegaDTO){
        return bodegaService.save(bodegaDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar bodega", description="Actualiza una bodega existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Bodega> update(
            @Parameter(description="ID de la bodega") @PathVariable Integer id,
            @Valid @RequestBody BodegaRequestDTO request) {
        Bodega bodegaActualizada = bodegaService.update(id, request);
        return ResponseEntity.ok(bodegaActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar bodega", description="Elimina una bodega")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID de la bodega") @PathVariable Integer id){
        if(!bodegaService.existsById(id)){
            log.warn("Intento eliminar bodega inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada");
        }
        bodegaService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una bodega")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID de la bodega") @PathVariable Integer id) {
        return ResponseEntity.ok(bodegaService.existsById(id));
    }
}