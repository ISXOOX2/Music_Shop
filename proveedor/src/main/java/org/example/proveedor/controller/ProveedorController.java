package org.example.proveedor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.proveedor.dto.ProveedorRequestDTO;
import org.example.proveedor.model.Proveedor;
import org.example.proveedor.service.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name="Proveedores", description="Gestión de marcas y distribuidoras")
public class ProveedorController {

    private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary="Obtener todos los proveedores", description="Retorna lista de todos los proveedores registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de proveedores obtenida correctamente")
    public List<Proveedor> findAll(){
        log.info("Obteniendo todos los proveedores");
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener proveedor por ID", description="Retorna un proveedor específico")
    @ApiResponse(responseCode="200", description="OK - Proveedor encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Proveedor no existe con ese ID")
    public Proveedor findById(
            @Parameter(description="ID del proveedor", required=true)
            @PathVariable Integer id){

        log.info("Buscando proveedor con ID: {}", id);
        return proveedorService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Proveedor no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Proveedor con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear proveedor", description="Crea un nuevo proveedor con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Proveedor creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: razonSocial, email, telefono")
    public Proveedor create(
            @Valid @RequestBody ProveedorRequestDTO proveedorDTO){

        log.info("Creando nuevo proveedor: {}", proveedorDTO.getRazonSocial());
        Proveedor proveedor = proveedorService.save(proveedorDTO);
        log.info("Proveedor creado con ID: {}", proveedor.getId());
        return proveedor;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar proveedor", description="Actualiza un proveedor existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Proveedor no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Proveedor> update(
            @Parameter(description="ID del proveedor", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequestDTO request) {

        log.info("Actualizando proveedor con ID: {}", id);

        if(!proveedorService.existsById(id)){
            log.warn("Intento actualizar proveedor inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Proveedor con ID " + id + " no existe");
        }

        Proveedor proveedorActualizado = proveedorService.update(id, request);
        log.info("Proveedor actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar proveedor", description="Elimina un proveedor del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Proveedor eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Proveedor no existe con ese ID")
    public void delete(
            @Parameter(description="ID del proveedor", required=true)
            @PathVariable Integer id){

        log.info("Eliminando proveedor con ID: {}", id);

        if(!proveedorService.existsById(id)){
            log.warn("Intento eliminar proveedor inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Proveedor con ID " + id + " no existe");
        }

        proveedorService.delete(id);
        log.info("Proveedor eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un proveedor con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del proveedor", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de proveedor con ID: {}", id);
        boolean existe = proveedorService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}