package org.example.proveedor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede exceder los 12 caracteres")
    private String rut;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 200, message = "La razón social no puede exceder los 200 caracteres")
    private String razonSocial;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    @Size(max = 200, message = "El email no puede exceder los 200 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 50, message = "El teléfono no puede exceder los 50 caracteres")
    private String telefono;
}
