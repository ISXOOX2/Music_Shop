package org.example.sucursal;

import org.example.sucursal.dto.SucursalRequestDTO;
import org.example.sucursal.model.Sucursal;
import org.example.sucursal.repository.SucursalRepository;
import org.example.sucursal.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SucursalServiceTest")
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalTest;
    private SucursalRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        sucursalTest = new Sucursal();
        sucursalTest.setId(1);
        sucursalTest.setNombre("Sucursal Sur");
        sucursalTest.setDireccion("Av. Secundaria 456");
        sucursalTest.setTelefono("56912345678");

        dtoTest = new SucursalRequestDTO();
        dtoTest.setNombre("Sucursal Sur");
        dtoTest.setDireccion("Av. Secundaria 456");
        dtoTest.setTelefono("56912345678");
    }

    @Test
    @DisplayName("Debe crear una sucursal exitosamente")
    void testCrearSucursalExitosa() {
        // Given
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalTest);

        // When
        Sucursal resultado = sucursalService.save(dtoTest);

        // Then
        assertNotNull(resultado);
        assertEquals("Sucursal Sur", resultado.getNombre());
        assertEquals("Av. Secundaria 456", resultado.getDireccion());
        assertEquals("56912345678", resultado.getTelefono());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Debe obtener todas las sucursales")
    void testObtenerTodasLasSucursales() {
        // Given
        List<Sucursal> sucursales = new ArrayList<>();
        sucursales.add(sucursalTest);

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setId(2);
        sucursal2.setNombre("Sucursal Norte");
        sucursal2.setDireccion("Av. Libertador 123");
        sucursal2.setTelefono("56987654321");
        sucursales.add(sucursal2);

        when(sucursalRepository.findAll()).thenReturn(sucursales);

        // When
        List<Sucursal> resultado = sucursalService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Sucursal Sur", resultado.get(0).getNombre());
        assertEquals("Sucursal Norte", resultado.get(1).getNombre());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener una sucursal por ID")
    void testObtenerSucursalPorId() {
        // Given
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursalTest));

        // When
        Optional<Sucursal> resultado = sucursalService.findById(1);

        // Then
        assertNotNull(resultado);
        assertEquals("Sucursal Sur", resultado.get().getNombre());
        verify(sucursalRepository, times(1)).findById(1);
    }
}
