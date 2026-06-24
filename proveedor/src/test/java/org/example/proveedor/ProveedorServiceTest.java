package org.example.proveedor;


import org.example.proveedor.dto.ProveedorRequestDTO;
import org.example.proveedor.model.Proveedor;
import org.example.proveedor.repository.ProveedorRepository;
import org.example.proveedor.service.ProveedorService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProveedorService - Pruebas Unitarias con Feign")
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedorTest;
    private ProveedorRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        proveedorTest = new Proveedor();
        proveedorTest.setId(1);
        proveedorTest.setRut("1234567891-3");
        proveedorTest.setRazonSocial("proveer mercancia");
        proveedorTest.setEmail("proveedor@hotmail.com");
        proveedorTest.setTelefono("123456789");

        dtoTest = new ProveedorRequestDTO();
        dtoTest.setEmail("proveedor@hotmail.com");
        dtoTest.setRazonSocial("proveer mercancia");
        dtoTest.setRut("1234567891-3");
        dtoTest.setTelefono("123456789");
    }

    @Test
    @DisplayName("debe crear un proveedor exitosamente")
    void testGuardarProveedorExitoso() {
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorTest);

        Proveedor resultado = proveedorService.save(dtoTest);

        assertNotNull(resultado);
        assertEquals("proveer mercancia", resultado.getRazonSocial());
        assertEquals("1234567891-3", resultado.getRut());
        assertEquals("proveedor@hotmail.com", resultado.getEmail());
        assertEquals("123456789", resultado.getTelefono());
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debe obtener proovedor por Id")
    void testObtenerProveedorPorId() {
        //given
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedorTest));

        //when
        Optional<Proveedor> resultado = proveedorService.findById(1);

        //the
        assertNotNull(resultado);
        assertEquals("proveer mercancia", resultado.get().getRazonSocial());
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe eliminar un proveedor exitosamente")
    void testEliminarPorveedorPorId(){
        //given
        Integer id = 1;

        //when
        proveedorService.delete(id);

        //then
        verify(proveedorRepository, times(1)).deleteById(id);
    }
}
