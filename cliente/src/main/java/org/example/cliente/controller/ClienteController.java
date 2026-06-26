package org.example.cliente.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.cliente.dto.ClienteRequestDTO;
import org.example.cliente.model.Cliente;
import org.example.cliente.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name="Clientes", description="Gestión de datos de clientes")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary="Obtener todos los clientes", description="Retorna lista de todos los clientes registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de clientes obtenida correctamente")
    public List<Cliente> findAll(){
        log.info("Obteniendo todos los clientes");
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener cliente por ID", description="Retorna un cliente específico")
    @ApiResponse(responseCode="200", description="OK - Cliente encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Cliente no existe con ese ID")
    public Cliente findById(
            @Parameter(description="ID del cliente", required=true)
            @PathVariable Integer id){

        log.info("Buscando cliente con ID: {}", id);
        return clienteService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Cliente con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear cliente", description="Crea un nuevo cliente con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Cliente creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombreCompleto, email, rut")
    public Cliente create(
            @Valid @RequestBody ClienteRequestDTO clienteDTO){

        log.info("Creando nuevo cliente: {}", clienteDTO.getNombreCompleto());
        Cliente cliente = clienteService.save(clienteDTO);
        log.info("Cliente creado con ID: {}", cliente.getId());
        return cliente;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar cliente", description="Actualiza los datos de un cliente existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Cliente no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombreCompleto, email, rut")
    public ResponseEntity<Cliente> update(
            @Parameter(description="ID del cliente", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {

        log.info("Actualizando cliente con ID: {}", id);

        if(!clienteService.existsById(id)){
            log.warn("Intento actualizar cliente inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Cliente con ID " + id + " no existe");
        }

        Cliente clienteActualizado = clienteService.update(id, request);
        log.info("Cliente actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar cliente", description="Elimina un cliente del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Cliente eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Cliente no existe con ese ID")
    public void delete(
            @Parameter(description="ID del cliente", required=true)
            @PathVariable Integer id){

        log.info("Eliminando cliente con ID: {}", id);

        if(!clienteService.existsById(id)){
            log.warn("Intento eliminar cliente inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Cliente con ID " + id + " no existe");
        }

        clienteService.delete(id);
        log.info("Cliente eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un cliente con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del cliente", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de cliente con ID: {}", id);
        boolean existe = clienteService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}