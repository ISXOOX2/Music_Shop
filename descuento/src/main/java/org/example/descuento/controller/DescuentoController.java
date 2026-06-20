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
    @Operation(summary="Obtener todos los descuentos", description="Retorna lista de todos los descuentos")
    @ApiResponse(responseCode="200", description="OK")
    public List<Descuento> findAll(){
        return descuentoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener descuento por ID", description="Retorna un descuento específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Descuento findById(@Parameter(description="ID del descuento") @PathVariable Integer id){
        return descuentoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Descuento no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Descuento no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear descuento", description="Crea un nuevo descuento")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Descuento create(@Valid @RequestBody DescuentoRequestDTO descuentoDTO){
        return descuentoService.save(descuentoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar descuento", description="Actualiza un descuento existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Descuento> update(
            @Parameter(description="ID del descuento") @PathVariable Integer id,
            @Valid @RequestBody DescuentoRequestDTO request) {
        Descuento descuentoActualizado = descuentoService.update(id, request);
        return ResponseEntity.ok(descuentoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar descuento", description="Elimina un descuento")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID del descuento") @PathVariable Integer id){
        if(!descuentoService.existsById(id)){
            log.warn("Intento eliminar descuento inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Descuento no encontrado");
        }
        descuentoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un descuento")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del descuento") @PathVariable Integer id) {
        return ResponseEntity.ok(descuentoService.existsById(id));
    }
}
