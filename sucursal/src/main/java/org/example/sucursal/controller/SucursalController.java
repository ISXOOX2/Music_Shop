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
    @Operation(summary="Obtener todas las sucursales", description="Retorna lista de todas las sucursales registradas")
    @ApiResponse(responseCode="200", description="OK - Lista de sucursales obtenida correctamente")
    public List<Sucursal> findAll(){
        log.info("Obteniendo todas las sucursales");
        return sucursalService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener sucursal por ID", description="Retorna una sucursal específica")
    @ApiResponse(responseCode="200", description="OK - Sucursal encontrada")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Sucursal no existe con ese ID")
    public Sucursal findById(
            @Parameter(description="ID de la sucursal", required=true)
            @PathVariable Integer id){

        log.info("Buscando sucursal con ID: {}", id);
        return sucursalService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Sucursal no encontrada: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Sucursal con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear sucursal", description="Crea una nueva sucursal con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Sucursal creada exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombre, direccion, telefono")
    public Sucursal create(
            @Valid @RequestBody SucursalRequestDTO sucursalDTO){

        log.info("Creando nueva sucursal: {}", sucursalDTO.getNombre());
        Sucursal sucursal = sucursalService.save(sucursalDTO);
        log.info("Sucursal creada con ID: {}", sucursal.getId());
        return sucursal;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar sucursal", description="Actualiza una sucursal existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Sucursal no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Sucursal> update(
            @Parameter(description="ID de la sucursal", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO request) {

        log.info("Actualizando sucursal con ID: {}", id);

        if(!sucursalService.existsById(id)){
            log.warn("Intento actualizar sucursal inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Sucursal con ID " + id + " no existe");
        }

        Sucursal sucursalActualizada = sucursalService.update(id, request);
        log.info("Sucursal actualizada exitosamente. ID: {}", id);
        return ResponseEntity.ok(sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar sucursal", description="Elimina una sucursal del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Sucursal eliminada exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Sucursal no existe con ese ID")
    public void delete(
            @Parameter(description="ID de la sucursal", required=true)
            @PathVariable Integer id){

        log.info("Eliminando sucursal con ID: {}", id);

        if(!sucursalService.existsById(id)){
            log.warn("Intento eliminar sucursal inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Sucursal con ID " + id + " no existe");
        }

        sucursalService.delete(id);
        log.info("Sucursal eliminada exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una sucursal con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID de la sucursal", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de sucursal con ID: {}", id);
        boolean existe = sucursalService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}