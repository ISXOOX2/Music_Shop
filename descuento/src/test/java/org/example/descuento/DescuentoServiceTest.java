package org.example.descuento.service;

import org.example.descuento.dto.DescuentoRequestDTO;
import org.example.descuento.model.Descuento;
import org.example.descuento.repository.DescuentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DescuentoServiceTest")
class DescuentoServiceTest {

    @Mock
    private DescuentoRepository descuentoRepository;

    @InjectMocks
    private DescuentoService descuentoService;

    private Descuento descuentoTest;
    private DescuentoRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        descuentoTest = new Descuento();
        descuentoTest.setId(1);
        descuentoTest.setCodigo("DESC20");
        descuentoTest.setPorcentaje(20.0);
        descuentoTest.setFechaExpiracion(LocalDateTime.now().plusDays(30));

        dtoTest = new DescuentoRequestDTO();
        dtoTest.setCodigo("DESC20");
        dtoTest.setPorcentaje(20.0);
        dtoTest.setFechaExpiracion(LocalDateTime.now().plusDays(30));
    }

    @Test
    @DisplayName("Debe crear un descuento exitosamente")
    void testCrearDescuentoExitoso() {
        // Given
        when(descuentoRepository.save(any(Descuento.class))).thenReturn(descuentoTest);

        // When
        Descuento resultado = descuentoService.save(dtoTest);

        // Then
        assertNotNull(resultado);
        assertEquals("DESC20", resultado.getCodigo());
        assertEquals(20.0, resultado.getPorcentaje());
        verify(descuentoRepository, times(1)).save(any(Descuento.class));
    }

    @Test
    @DisplayName("Debe obtener un descuento por ID")
    void testObtenerDescuentoPorId() {
        // Given
        when(descuentoRepository.findById(1)).thenReturn(Optional.of(descuentoTest));

        // When
        Optional<Descuento> resultado = descuentoService.findById(1);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("DESC20", resultado.get().getCodigo());
        assertEquals(20.0, resultado.get().getPorcentaje());
        verify(descuentoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe verificar si descuento existe")
    void testVerificarExistenciaDescuento() {
        // Given
        when(descuentoRepository.existsById(1)).thenReturn(true);
        when(descuentoRepository.existsById(999)).thenReturn(false);

        // When - Then
        assertTrue(descuentoService.existsById(1));
        assertFalse(descuentoService.existsById(999));
        verify(descuentoRepository, times(1)).existsById(1);
        verify(descuentoRepository, times(1)).existsById(999);
    }
}
