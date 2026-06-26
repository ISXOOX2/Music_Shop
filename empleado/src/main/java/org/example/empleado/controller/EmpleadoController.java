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
    @Operation(summary="Obtener todos los empleados", description="Retorna lista de todos los empleados registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de empleados obtenida correctamente")
    public List<Empleado> findAll(){
        log.info("Obteniendo todos los empleados");
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener empleado por ID", description="Retorna un empleado específico")
    @ApiResponse(responseCode="200", description="OK - Empleado encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Empleado no existe con ese ID")
    public Empleado findById(
            @Parameter(description="ID del empleado", required=true)
            @PathVariable Integer id){

        log.info("Buscando empleado con ID: {}", id);
        return empleadoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Empleado no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Empleado con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Crear empleado", description="Crea un nuevo empleado con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Empleado creado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombreCompleto, rut, cargo, salario")
    public Empleado create(
            @Valid @RequestBody EmpleadoRequestDTO empleadoDTO){

        log.info("Creando nuevo empleado: {}", empleadoDTO.getNombreCompleto());
        Empleado empleado = empleadoService.save(empleadoDTO);
        log.info("Empleado creado con ID: {}", empleado.getId());
        return empleado;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar empleado", description="Actualiza los datos de un empleado existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Empleado no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: nombreCompleto, rut, cargo, salario")
    public ResponseEntity<Empleado> update(
            @Parameter(description="ID del empleado", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequestDTO request) {

        log.info("Actualizando empleado con ID: {}", id);

        if(!empleadoService.existsById(id)){
            log.warn("Intento actualizar empleado inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Empleado con ID " + id + " no existe");
        }

        Empleado empleadoActualizado = empleadoService.update(id, request);
        log.info("Empleado actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar empleado", description="Elimina un empleado del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Empleado eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Empleado no existe con ese ID")
    public void delete(
            @Parameter(description="ID del empleado", required=true)
            @PathVariable Integer id){

        log.info("Eliminando empleado con ID: {}", id);

        if(!empleadoService.existsById(id)){
            log.warn("Intento eliminar empleado inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Empleado con ID " + id + " no existe");
        }

        empleadoService.delete(id);
        log.info("Empleado eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un empleado con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del empleado", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de empleado con ID: {}", id);
        boolean existe = empleadoService.existsById(id);
        return ResponseEntity.ok(existe);
    }
}