package org.example.empleado.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede exceder los 12 caracteres")
    private String rut;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 200, message = "El cargo no puede exceder los 200 caracteres")
    private String cargo;

    @NotNull(message = "El salario es obligatorio")
    @Min(value = 1, message = "El salario debe ser mayor a 0")
    private Integer salario;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 200, message = "El nombre completo no puede exceder los 200 caracteres")
    private String nombreCompleto;
}