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
    @Operation(summary="Obtener todos los productos", description="Retorna lista de todos los productos del catálogo")
    @ApiResponse(responseCode="200", description="OK - Lista de productos obtenida correctamente")
    public List<Producto> findAll(){
        log.info("Obteniendo todos los productos");
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener producto por ID", description="Retorna un producto específico")
    @ApiResponse(responseCode="200", description="OK - Producto encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Producto no existe con ese ID")
    public Producto findById(
            @Parameter(description="ID del producto", required=true)
            @PathVariable Integer id){

        log.info("Buscando producto con ID: {}", id);
        return productoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Producto con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear producto", description="Crea un nuevo producto con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Producto creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombre, precio")
    public Producto create(
            @Valid @RequestBody ProductoRequestDTO productoDTO){

        log.info("Creando nuevo producto: {}", productoDTO.getNombre());
        Producto producto = productoService.save(productoDTO);
        log.info("Producto creado con ID: {}", producto.getId());
        return producto;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar producto", description="Actualiza un producto existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Producto no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Producto> update(
            @Parameter(description="ID del producto", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequestDTO request) {

        log.info("Actualizando producto con ID: {}", id);

        if(!productoService.existsById(id)){
            log.warn("Intento actualizar producto inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Producto con ID " + id + " no existe");
        }

        Producto productoActualizado = productoService.update(id, request);
        log.info("Producto actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar producto", description="Elimina un producto del catálogo")
    @ApiResponse(responseCode="204", description="ELIMINADO - Producto eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Producto no existe con ese ID")
    public void delete(
            @Parameter(description="ID del producto", required=true)
            @PathVariable Integer id){

        log.info("Eliminando producto con ID: {}", id);

        if(!productoService.existsById(id)){
            log.warn("Intento eliminar producto inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Producto con ID " + id + " no existe");
        }

        productoService.delete(id);
        log.info("Producto eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un producto con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del producto", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de producto con ID: {}", id);
        boolean existe = productoService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}