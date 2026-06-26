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
    @ApiResponse(responseCode="200", description="OK - Lista de bodegas obtenida correctamente")
    public List<Bodega> findAll(){
        log.info("Obteniendo todas las bodegas");
        return bodegaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener bodega por ID", description="Retorna una bodega específica")
    @ApiResponse(responseCode="200", description="OK - Bodega encontrada")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Bodega no existe")
    public Bodega findById(
            @Parameter(description="ID de la bodega", required=true)
            @PathVariable Integer id){

        log.info("Buscando bodega con ID: {}", id);
        return bodegaService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bodega no encontrada: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Bodega con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear bodega", description="Crea una nueva bodega")
    @ApiResponse(responseCode="201", description="CREADO - Bodega creada exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombre, capacidadMaxima")
    public Bodega create(
            @Valid @RequestBody BodegaRequestDTO bodegaDTO){

        log.info("Creando nueva bodega: {}", bodegaDTO.getNombre());
        Bodega bodega = bodegaService.save(bodegaDTO);
        log.info("Bodega creada con ID: {}", bodega.getId());
        return bodega;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar bodega", description="Actualiza una bodega existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Bodega no existe")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Bodega> update(
            @Parameter(description="ID de la bodega", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody BodegaRequestDTO request) {

        log.info("Actualizando bodega con ID: {}", id);

        if(!bodegaService.existsById(id)){
            log.warn("Intento actualizar bodega inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Bodega con ID " + id + " no existe");
        }

        Bodega bodegaActualizada = bodegaService.update(id, request);
        log.info("Bodega actualizada exitosamente. ID: {}", id);
        return ResponseEntity.ok(bodegaActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar bodega", description="Elimina una bodega")
    @ApiResponse(responseCode="204", description="ELIMINADO - Bodega eliminada exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Bodega no existe")
    public void delete(
            @Parameter(description="ID de la bodega", required=true)
            @PathVariable Integer id){

        log.info("Eliminando bodega con ID: {}", id);

        if(!bodegaService.existsById(id)){
            log.warn("Intento eliminar bodega inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Bodega con ID " + id + " no existe");
        }

        bodegaService.delete(id);
        log.info("Bodega eliminada exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una bodega")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID de la bodega", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de bodega con ID: {}", id);
        boolean existe = bodegaService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}