package org.example.empleado.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.empleado.dto.EmpleadoRequestDTO;
import org.example.empleado.model.Empleado;
import org.example.empleado.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Tag(name="Empleados", description="Control de personal y accesos")
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary="Obtener todos los empleados", description="Retorna lista de todos los empleados")
    @ApiResponse(responseCode="200", description="OK")
    public List<Empleado> findAll(){
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener empleado por ID", description="Retorna un empleado específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Empleado findById(@Parameter(description="ID del empleado") @PathVariable Integer id){
        return empleadoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Empleado no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear empleado", description="Crea un nuevo empleado")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Empleado create(@Valid @RequestBody EmpleadoRequestDTO empleadoDTO){
        return empleadoService.save(empleadoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar empleado", description="Actualiza un empleado existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<Empleado> update(
            @Parameter(description="ID del empleado") @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequestDTO request) {
        Empleado empleadoActualizado = empleadoService.update(id, request);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar empleado", description="Elimina un empleado")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID del empleado") @PathVariable Integer id){
        if(!empleadoService.existsById(id)){
            log.warn("Intento eliminar empleado inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
        }
        empleadoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un empleado")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del empleado") @PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.existsById(id));
    }
}