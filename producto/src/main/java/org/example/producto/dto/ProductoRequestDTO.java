package org.example.producto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
    private String nombre;

    @NotBlank(message = "El formato no puede estar vacío")
    @Size(max = 100, message = "El formato no puede exceder los 100 caracteres")
    private String formato;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @Size(max = 100, message = "La categoría no puede exceder los 100 caracteres")
    private String categoria;

    @Size(max = 100, message = "La marca no puede exceder los 100 caracteres")
    private String marca;
}