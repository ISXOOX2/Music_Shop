package org.example.empleado;

import org.example.empleado.dto.EmpleadoRequestDTO;
import org.example.empleado.model.Empleado;
import org.example.empleado.repository.EmpleadoRepository;
import org.example.empleado.service.EmpleadoService;
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
@DisplayName("EmpleadoServiceTest")
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleadoTest;
    private EmpleadoRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        empleadoTest = new Empleado();
        empleadoTest.setId(1);
        empleadoTest.setNombreCompleto("Carlos López");
        empleadoTest.setRut("98765432-1");
        empleadoTest.setCargo("Vendedor");
        empleadoTest.setSalario(800000);

        dtoTest = new EmpleadoRequestDTO();
        dtoTest.setNombreCompleto("Carlos López");
        dtoTest.setRut("98765432-1");
        dtoTest.setCargo("Vendedor");
        dtoTest.setSalario(800000);
    }

    @Test
    @DisplayName("Debe crear un empleado exitosamente")
    void testCrearEmpleadoExitoso() {
        // Given
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoTest);

        // When
        Empleado resultado = empleadoService.save(dtoTest);

        // Then
        assertNotNull(resultado);
        assertEquals("Carlos López", resultado.getNombreCompleto());
        assertEquals("Vendedor", resultado.getCargo());
        assertEquals(800000, resultado.getSalario());
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    @DisplayName("Debe obtener un empleado por ID existente")
    void testObtenerEmpleadoPorIdExistente() {
        // Given
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleadoTest));

        // When
        Optional<Empleado> resultado = empleadoService.findById(1);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Carlos López", resultado.get().getNombreCompleto());
        assertEquals("Vendedor", resultado.get().getCargo());
        verify(empleadoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe actualizar un empleado correctamente")
    void testActualizarEmpleado() {
        // Given
        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setId(1);
        empleadoActualizado.setNombreCompleto("Carlos López");
        empleadoActualizado.setRut("98765432-1");
        empleadoActualizado.setCargo("Supervisor");  // Cambio de cargo
        empleadoActualizado.setSalario(950000);      // Cambio de salario


        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoActualizado);

        EmpleadoRequestDTO dtoActualizado = new EmpleadoRequestDTO();
        dtoActualizado.setNombreCompleto("Carlos López");
        dtoActualizado.setRut("98765432-1");
        dtoActualizado.setCargo("Supervisor");
        dtoActualizado.setSalario(950000);

        // When
        Empleado resultado = empleadoService.update(1, dtoActualizado);

        // Then
        assertEquals("Supervisor", resultado.getCargo());
        assertEquals(950000, resultado.getSalario());
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }
}
