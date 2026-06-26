package org.example.pago.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.pago.dto.PagoRequestDTO;
import org.example.pago.model.Pago;
import org.example.pago.service.PagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name="Pagos", description="Procesamiento de transacciones financieras")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(summary="Obtener todos los pagos", description="Retorna lista de todos los pagos registrados")
    @ApiResponse(responseCode="200", description="OK - Lista de pagos obtenida correctamente")
    public List<Pago> findAll(){
        log.info("Obteniendo todos los pagos");
        List<Pago> pagos = pagoService.findAll();
        log.info("Se obtuvieron {} pagos", pagos.size());
        return pagos;
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener pago por ID", description="Retorna un pago específico")
    @ApiResponse(responseCode="200", description="OK - Pago encontrado")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pago no existe con ese ID")
    public Pago findById(
            @Parameter(description="ID del pago", required=true)
            @PathVariable Integer id){

        log.info("Buscando pago con ID: {}", id);
        return pagoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Error 404: Pago con ID " + id + " no existe");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Procesar pago", description="Registra un nuevo pago con validación de datos")
    @ApiResponse(responseCode="201", description="CREADO - Pago procesado exitosamente")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS - Campos requeridos: monto, metodoPago, pedidoId")
    public Pago create(
            @Valid @RequestBody PagoRequestDTO pagoDTO){

        log.info("Procesando nuevo pago - Monto: {}", pagoDTO.getMontoPagado());
        Pago pago = pagoService.save(pagoDTO);
        log.info("Pago procesado con ID: {}", pago.getId());
        return pago;
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar pago", description="Actualiza los datos de un pago existente")
    @ApiResponse(responseCode="200", description="OK - Actualizado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pago no existe con ese ID")
    @ApiResponse(responseCode="400", description="DATOS INVÁLIDOS")
    public ResponseEntity<Pago> actualizar(
            @Parameter(description="ID del pago", required=true)
            @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO request) {

        log.info("Actualizando pago con ID: {}", id);

        if(!pagoService.existePorId(id)){
            log.warn("Intento actualizar pago inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Pago con ID " + id + " no existe");
        }

        Pago pagoActualizado = pagoService.actualizar(id, request);
        log.info("Pago actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar pago", description="Elimina un registro de pago del sistema")
    @ApiResponse(responseCode="204", description="ELIMINADO - Pago eliminado exitosamente")
    @ApiResponse(responseCode="404", description="NO ENCONTRADO - Pago no existe con ese ID")
    public void delete(
            @Parameter(description="ID del pago", required=true)
            @PathVariable Integer id){

        log.info("Eliminando pago con ID: {}", id);

        if(!pagoService.existePorId(id)){
            log.warn("Intento eliminar pago inexistente: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Pago con ID " + id + " no existe");
        }

        pagoService.delete(id);
        log.info("Pago eliminado exitosamente. ID: {}", id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un pago con ese ID")
    @ApiResponse(responseCode="200", description="OK - Retorna true o false")
    public ResponseEntity<Boolean> existe(
            @Parameter(description="ID del pago", required=true)
            @PathVariable Integer id) {

        log.info("Verificando existencia de pago con ID: {}", id);
        boolean existe = pagoService.existePorId(id);
        return ResponseEntity.ok(existe);
    }
}