package org.example.bodega;

import org.example.bodega.client.SucursalClient;
import org.example.bodega.dto.BodegaRequestDTO;
import org.example.bodega.model.Bodega;
import org.example.bodega.repository.BodegaRepository;
import org.example.bodega.service.BodegaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BodegaService - Pruebas Unitarias con Feign")
class BodegaServiceTest {

    @Mock
    private BodegaRepository bodegaRepository;

    @Mock
    private SucursalClient sucursalClient;

    @InjectMocks
    private BodegaService bodegaService;

    private Bodega bodegaTest;
    private BodegaRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        bodegaTest = new Bodega();
        bodegaTest.setId(1);
        bodegaTest.setNombre("Bodega Central");
        bodegaTest.setCapacidadMaxima(1000);
        bodegaTest.setSucursalId(1);

        dtoTest = new BodegaRequestDTO();
        dtoTest.setNombre("Bodega Central");
        dtoTest.setCapacidadMaxima(1000);
        dtoTest.setSucursalId(1);
    }

    @Test
    @DisplayName("Debe Crear Una Bodega Exitosamente")
    void testCrearBodegaExitoso() {
        //given
        when(sucursalClient.existeSucursalPorId(anyInt())).thenReturn(true);
        when(bodegaRepository.save(any(Bodega.class))).thenReturn(bodegaTest);

        //when
        Bodega resultado = bodegaService.save(dtoTest);

        //then
        assertNotNull(resultado);
        assertEquals("Bodega Central", resultado.getNombre());
        assertEquals(1000, resultado.getCapacidadMaxima());
        assertEquals(1, resultado.getSucursalId());
        verify(bodegaRepository, times(1)).save(any(Bodega.class));
    }

    @Test
    @DisplayName("Debe rechazar la creación si la sucursal no existe")
    void testCrearBodegaSucursalInexistente() {
        //given
        when(sucursalClient.existeSucursalPorId(anyInt())).thenReturn(false);

        //when & then
        assertThrows(org.springframework.web.server.ResponseStatusException.class,
                () -> bodegaService.save(dtoTest));
        verify(bodegaRepository, never()).save(any(Bodega.class));
    }

    @Test
    @DisplayName("Debe obtener una bodega por ID existente")
    void testBuscarBodegaPorId(){
        //given
        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodegaTest));

        //when
        Optional<Bodega> resultado = bodegaService.findById(1);

        //then
        assertTrue(resultado.isPresent());
        assertEquals("Bodega Central", resultado.get().getNombre());
        verify(bodegaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar Optional vacío cuando bodega no existe")
    void testObtenerBodegaPorId(){
        //given
        when(bodegaRepository.findById(999)).thenReturn(Optional.empty());

        //when
        Optional<Bodega> resultado = bodegaService.findById(999);

        //then
        assertFalse(resultado.isPresent());
        verify(bodegaRepository, times(1)).findById(999);
    }
}