package org.example.devolucionGarantia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.devolucionGarantia.dto.DevolucionGarantiaDTO;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/devoluciones")
@Tag(name="Devoluciones y Garantía", description="Gestión de devoluciones y garantía")
public class DevolucionGarantiaController {

    private static final Logger log = LoggerFactory.getLogger(DevolucionGarantiaController.class);

    @Autowired
    private DevolucionGarantiaService devolucionService;

    @GetMapping
    @Operation(summary="Obtener todas las devoluciones", description="Retorna lista de todas las devoluciones")
    @ApiResponse(responseCode="200", description="OK")
    public List<DevolucionGarantia> listarTodos(){
        return devolucionService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener devolución por ID", description="Retorna una devolución específica")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public DevolucionGarantia obtenerPorId(@Parameter(description="ID de la devolución") @PathVariable Integer id){
        return devolucionService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Registrar devolución", description="Registra una nueva solicitud de devolución")
    @ApiResponse(responseCode="201", description="Creado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public DevolucionGarantia registrarSolicitud(@Valid @RequestBody DevolucionGarantiaDTO dto){
        return devolucionService.registrarSolicitud(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar devolución", description="Actualiza una devolución existente")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="No encontrado")
    public ResponseEntity<DevolucionGarantia> actualizar(
            @Parameter(description="ID de la devolución") @PathVariable Integer id,
            @Valid @RequestBody DevolucionGarantia request) {
        DevolucionGarantia devolucionActualizada = devolucionService.actualizar(id, request);
        return ResponseEntity.ok(devolucionActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar devolución", description="Elimina una devolución")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void eliminar(@Parameter(description="ID de la devolución") @PathVariable Integer id){
        devolucionService.eliminar(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una devolución")
    public ResponseEntity<Boolean> existePorId(@Parameter(description="ID de la devolución") @PathVariable Integer id) {
        return ResponseEntity.ok(devolucionService.existePorId(id));
    }
}
