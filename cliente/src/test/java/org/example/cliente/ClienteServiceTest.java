package org.example.cliente;

import org.example.cliente.dto.ClienteRequestDTO;
import org.example.cliente.model.Cliente;
import org.example.cliente.repository.ClienteRepository;
import org.example.cliente.service.ClienteService;
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
@DisplayName("ClienteServiceTest")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
   private ClienteService clienteService;

    private Cliente clienteTest;
    private ClienteRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        clienteTest = new Cliente();
        clienteTest.setId(1);
        clienteTest.setNombreCompleto("Juan Pérez");
        clienteTest.setEmail("juan@example.com");
        clienteTest.setRut("12345678-9");

        dtoTest = new ClienteRequestDTO();
        dtoTest.setNombreCompleto("Juan Pérez");
        dtoTest.setEmail("juan@example.com");
        dtoTest.setRut("12345678-9");
    }

    @Test
    @DisplayName("Debe crear un cliente exitosamente")
    void testCrearClienteExitoso() {
        // Given
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteTest);

        // When
        Cliente resultado = clienteService.save(dtoTest);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals("juan@example.com", resultado.getEmail());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe obtener un cliente por ID existente")
    void testObtenerClientePorIdExistente() {
        // Given
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteTest));

        // When
        Optional<Cliente> resultado = clienteService.findById(1);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombreCompleto());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe eliminar un cliente exitosamente")
    void testEliminarCliente() {
        // Given
        Integer idCliente = 1;

        // When
        clienteService.delete(idCliente);

        // Then
        verify(clienteRepository, times(1)).deleteById(idCliente);
    }
}