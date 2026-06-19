package org.example.producto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.producto.dto.ProductoRequestDTO;
import org.example.producto.model.Producto;
import org.example.producto.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name="Productos", description="Gestión del catálogo de productos")
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary="Obtener todos los productos", description="Retorna lista de todos los productos")
    @ApiResponse(responseCode="200", description="OK")
    public List<Producto> findAll(){
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener producto por ID", description="Retorna un producto específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Producto findById(
            @Parameter(description="ID del producto") @PathVariable Integer id){
        return productoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear producto", description="Crea un nuevo producto")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Producto create(@Valid @RequestBody ProductoRequestDTO productoDTO){
        return productoService.save(productoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar producto", description="Actualiza un producto existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Producto> update(
            @Parameter(description="ID del producto") @PathVariable Integer id,
            @Valid @RequestBody ProductoRequestDTO request) {
        Producto productoActualizado = productoService.update(id, request);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar producto", description="Elimina un producto del catálogo")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public void delete(@Parameter(description="ID del producto") @PathVariable Integer id){
        if(!productoService.existsById(id)){
            log.warn("Intento eliminar producto inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        productoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un producto")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del producto") @PathVariable Integer id) {
        return ResponseEntity.ok(productoService.existsById(id));
    }
}