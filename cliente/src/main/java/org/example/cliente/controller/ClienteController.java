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
    @Operation(summary="Obtener todos los clientes", description="Retorna lista de todos los clientes")
    @ApiResponse(responseCode="200", description="OK")
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener cliente por ID", description="Retorna un cliente específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Cliente findById(@Parameter(description="ID del cliente") @PathVariable Integer id){
        return clienteService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear cliente", description="Crea un nuevo cliente")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Cliente create(@Valid @RequestBody ClienteRequestDTO clienteDTO){
        return clienteService.save(clienteDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar cliente", description="Actualiza un cliente existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Cliente> update(
            @Parameter(description="ID del cliente") @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {
        Cliente clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar cliente", description="Elimina un cliente")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID del cliente") @PathVariable Integer id){
        if(!clienteService.existsById(id)){
            log.warn("Intento eliminar cliente inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        clienteService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un cliente")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del cliente") @PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.existsById(id));
    }
}