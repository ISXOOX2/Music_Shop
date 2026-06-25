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
    @Operation(summary="Obtener todas las devoluciones", description="Retorna lista de todas las solicitudes de devolución registradas")
    @ApiResponse(responseCode="200", description="OK - Lista de devoluciones obtenida correctamente")
    public List<DevolucionGarantia> listarTodos(){
        log.info("Obteniendo todas las devoluciones");
        return devolucionService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener devolución por ID", description="Retorna una devolución específica")
    @ApiResponse(responseCode="200", description="OK - Devolución encontrada")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Devolución no existe con ese ID")
    public DevolucionGarantia obtenerPorId(
            @Parameter(description="ID de la devolución", required=true)
            @PathVariable Integer id){

        log.info("Buscando devolución con ID: {}", id);
        return devolucionService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Registrar devolución", description="Registra una nueva solicitud de devolución con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Solicitud de devolución registrada exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: idPedido, tipoSolicitud, motivo, idInventario, cantidad")
    public DevolucionGarantia registrarSolicitud(
            @Valid @RequestBody DevolucionGarantiaDTO dto){

        log.info("Registrando nueva solicitud de devolución para pedido: {}", dto.getIdPedido());
        DevolucionGarantia devolucion = devolucionService.registrarSolicitud(dto);
        log.info("Devolución registrada con ID: {}", devolucion.getId());
        return devolucion;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar devolución", description="Actualiza los datos de una devolución existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Devolución no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: tipoSolicitud, motivo, estadoResolucion")
    public ResponseEntity<DevolucionGarantia> actualizar(
            @Parameter(description="ID de la devolución", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody DevolucionGarantia request) {

        log.info("Actualizando devolución con ID: {}", id);

        if(!devolucionService.existePorId(id)){
            log.warn("Intento actualizar devolución inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Devolución con ID " + id + " no existe");
        }

        DevolucionGarantia devolucionActualizada = devolucionService.actualizar(id, request);
        log.info("Devolución actualizada exitosamente. ID: {}", id);
        return ResponseEntity.ok(devolucionActualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar devolución", description="Elimina una solicitud de devolución del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Devolución eliminada exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Devolución no existe con ese ID")
    public void eliminar(
            @Parameter(description="ID de la devolución", required=true)
            @PathVariable Integer id){

        log.info("Eliminando devolución con ID: {}", id);

        if(!devolucionService.existePorId(id)){
            log.warn("Intento eliminar devolución inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Devolución con ID " + id + " no existe");
        }

        devolucionService.eliminar(id);
        log.info("Devolución eliminada exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe una devolución con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existePorId(
            @Parameter(description="ID de la devolución", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de devolución con ID: {}", id);
        boolean existe = devolucionService.existePorId(id);
        return ResponseEntity.ok(existe);
    }
}
