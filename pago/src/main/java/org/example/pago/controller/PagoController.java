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
    @ApiResponse(responseCode="200", description="OK")
    public List<Pago> findAll(){
        List<Pago> pagos = pagoService.findAll();
        log.info("Se obtuvieron {} pagos", pagos.size());
        return pagos;
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener pago por ID", description="Retorna un pago específico")
    @ApiResponse(responseCode="200", description="OK")
    @ApiResponse(responseCode="404", description="No encontrado")
    public Pago findById(@Parameter(description="ID del pago") @PathVariable Integer id){
        return pagoService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago no encontrado: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Procesar pago", description="Registra un nuevo pago")
    @ApiResponse(responseCode="201", description="Pago procesado exitosamente")
    @ApiResponse(responseCode="400", description="Datos inválidos")
    public Pago create(@Valid @RequestBody PagoRequestDTO pagoDTO){
        return pagoService.save(pagoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar pago", description="Actualiza los datos de un pago")
    @ApiResponse(responseCode="200", description="Actualizado exitosamente")
    public ResponseEntity<Pago> actualizar(
            @Parameter(description="ID del pago") @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO request) {
        Pago pagoActualizado = pagoService.actualizar(id, request);
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary="Eliminar pago", description="Elimina un registro de pago")
    @ApiResponse(responseCode="204", description="Eliminado exitosamente")
    public void delete(@Parameter(description="ID del pago") @PathVariable Integer id){
        pagoService.delete(id);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary="Verificar existencia", description="Verifica si existe un pago")
    public ResponseEntity<Boolean> existe(@Parameter(description="ID del pago") @PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.existePorId(id));
    }
}