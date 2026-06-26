package org.example.descuento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.descuento.dto.DescuentoRequestDTO;
import org.example.descuento.model.Descuento;
import org.example.descuento.service.DescuentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
@Tag(name="Descuentos", description="Administración de rebajas y cupones")
public class DescuentoController {

    private static final Logger log = LoggerFactory.getLogger(DescuentoController.class);

    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    @Operation(summary="Obtener todos los descuentos", description="Retorna lista de todos los descuentos disponibles")
    @ApiResponse(responseCode="200", description="OK - Lista de descuentos obtenida correctamente")
    public List<Descuento> findAll(){
        log.info("Obteniendo todos los descuentos");
        return descuentoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener descuento por ID", description="Retorna un descuento específico")
    @ApiResponse(responseCode="200", description="OK - Descuento encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Descuento no existe con ese ID")
    public Descuento findById(
            @Parameter(description="ID del descuento", required=true)
            @PathVariable Integer id){

        log.info("Buscando descuento con ID: {}", id);
        return descuentoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Descuento no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Descuento con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear descuento", description="Crea un nuevo descuento con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Descuento creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: codigo, porcentaje, fechaExpiracion")
    public Descuento create(
            @Valid @RequestBody DescuentoRequestDTO descuentoDTO){

        log.info("Creando nuevo descuento: {}", descuentoDTO.getCodigo());
        Descuento descuento = descuentoService.save(descuentoDTO);
        log.info("Descuento creado con ID: {}", descuento.getId());
        return descuento;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar descuento", description="Actualiza los datos de un descuento existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Descuento no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: codigo, porcentaje, fechaExpiracion")
    public ResponseEntity<Descuento> update(
            @Parameter(description="ID del descuento", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody DescuentoRequestDTO request) {

        log.info("Actualizando descuento con ID: {}", id);

        if(!descuentoService.existsById(id)){
            log.warn("Intento actualizar descuento inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Descuento con ID " + id + " no existe");
        }

        Descuento descuentoActualizado = descuentoService.update(id, request);
        log.info("Descuento actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(descuentoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar descuento", description="Elimina un descuento del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Descuento eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Descuento no existe con ese ID")
    public void delete(
            @Parameter(description="ID del descuento", required=true)
            @PathVariable Integer id){

        log.info("Eliminando descuento con ID: {}", id);

        if(!descuentoService.existsById(id)){
            log.warn("Intento eliminar descuento inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Descuento con ID " + id + " no existe");
        }

        descuentoService.delete(id);
        log.info("Descuento eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un descuento con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del descuento", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de descuento con ID: {}", id);
        boolean existe = descuentoService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}
