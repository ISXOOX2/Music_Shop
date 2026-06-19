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
    @Operation(summary="Obtener todos los proveedores", description="Retorna lista de todos los proveedores")
    @ApiResponse(responseCode="200", description="OK")
    public List<Proveedor> findAll(){
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener proveedor por ID", description="Retorna un proveedor específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Proveedor findById(@Parameter(description="ID del proveedor") @PathVariable Integer id){
        return proveedorService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Proveedor no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear proveedor", description="Crea un nuevo proveedor")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Proveedor create(@Valid @RequestBody ProveedorRequestDTO proveedorDTO){
        return proveedorService.save(proveedorDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar proveedor", description="Actualiza un proveedor existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Proveedor> update(
            @Parameter(description="ID del proveedor") @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequestDTO request) {
        Proveedor proveedorActualizado = proveedorService.update(id, request);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar proveedor", description="Elimina un proveedor")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID del proveedor") @PathVariable Integer id){
        if(!proveedorService.existsById(id)){
            log.warn("Intento eliminar proveedor inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
        }
        proveedorService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un proveedor")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del proveedor") @PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.existsById(id));
    }
}