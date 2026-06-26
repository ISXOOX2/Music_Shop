package org.example.pedido;

import org.example.pedido.dto.PedidoRequestDTO;
import org.example.pedido.model.Pedido;
import org.example.pedido.repository.PedidoRepository;
import org.example.pedido.service.PedidoService;
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
@DisplayName("PedidoService - Pruebas Unitarias")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoTest;
    private PedidoRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        // Given - Preparar datos de prueba
        pedidoTest = new Pedido();
        pedidoTest.setId(1);
        pedidoTest.setClienteId(10);
        pedidoTest.setTotalFinal(500000);
        pedidoTest.setEstadoPedido("PENDIENTE");
        pedidoTest.setFechaEmision(LocalDateTime.now());

        dtoTest = new PedidoRequestDTO();
        dtoTest.setClienteId(10);
        dtoTest.setTotalFinal(500000);
        dtoTest.setEstadoPedido("PENDIENTE");
    }

    @Test
    @DisplayName("Debe crear un pedido exitosamente cuando los datos son válidos")
    void testGuardarPedidoExitoso() {
        // Given
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoTest);

        // When
        Pedido resultado = pedidoService.save(dtoTest);

        // Then
        assertNotNull(resultado, "El pedido no debe ser null");
        assertEquals(10, resultado.getClienteId(), "El ID del cliente debe ser 10");
        assertEquals(500000, resultado.getTotalFinal(), "El total debe ser 500000");
        assertEquals("PENDIENTE", resultado.getEstadoPedido());
        assertNotNull(resultado.getFechaEmision(), "La fecha de emisión debe estar establecida");

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debe obtener un pedido por ID cuando existe")
    void testBuscarPedidoPorIdExistente() {
        // Given
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoTest));

        // When
        Optional<Pedido> resultado = pedidoService.findById(1);

        // Then
        assertTrue(resultado.isPresent(), "El pedido debe existir");
        assertEquals(10, resultado.get().getClienteId());
        verify(pedidoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar Optional vacío cuando el pedido no existe")
    void testBuscarPedidoPorIdNoExistente() {
        // Given
        when(pedidoRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Pedido> resultado = pedidoService.findById(999);

        // Then
        assertFalse(resultado.isPresent(), "El Optional debe estar vacío");
        verify(pedidoRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debe actualizar un pedido correctamente")
    void testActualizarPedido() {
        // Given
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoTest));

        PedidoRequestDTO dtoActualizado = new PedidoRequestDTO();
        dtoActualizado.setClienteId(10);
        dtoActualizado.setTotalFinal(550000);
        dtoActualizado.setEstadoPedido("EN_PREPARACION");

        Pedido pedidoActualizado = new Pedido();
        pedidoActualizado.setId(1);
        pedidoActualizado.setClienteId(10);
        pedidoActualizado.setTotalFinal(550000);
        pedidoActualizado.setEstadoPedido("EN_PREPARACION");

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoActualizado);

        // When
        Pedido resultado = pedidoService.actualizar(1, dtoActualizado);

        // Then
        assertEquals(550000, resultado.getTotalFinal(), "El total debe actualizarse a 550000");
        assertEquals("EN_PREPARACION", resultado.getEstadoPedido());

        verify(pedidoRepository, times(1)).findById(1);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando intenta actualizar un pedido inexistente")
    void testActualizarPedidoNoExistenteLanzaExcepcion() {
        // Given
        when(pedidoRepository.findById(999)).thenReturn(Optional.empty());

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setClienteId(10);
        dto.setTotalFinal(500000);
        dto.setEstadoPedido("PENDIENTE");

        // When - Then
        assertThrows(Exception.class, () -> pedidoService.actualizar(999, dto),
                "Debe lanzar excepción cuando el pedido no existe");

        verify(pedidoRepository, times(1)).findById(999);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un pedido correctamente")
    void testEliminarPedido() {
        // Given
        Integer idPedido = 1;

        // When
        pedidoService.delete(idPedido);

        // Then
        verify(pedidoRepository, times(1)).deleteById(idPedido);
    }

    @Test
    @DisplayName("Debe verificar existencia de pedido correctamente")
    void testVerificarExistenciaPedido() {
        // Given
        when(pedidoRepository.existsById(1)).thenReturn(true);
        when(pedidoRepository.existsById(999)).thenReturn(false);

        // When - Then
        assertTrue(pedidoService.existsById(1), "Pedido con ID 1 debe existir");
        assertFalse(pedidoService.existsById(999), "Pedido con ID 999 no debe existir");

        verify(pedidoRepository, times(1)).existsById(1);
        verify(pedidoRepository, times(1)).existsById(999);
    }

    @Test
    @DisplayName("Debe obtener todos los pedidos")
    void testObtenerTodosPedidos() {
        // Given
        java.util.List<Pedido> pedidos = new java.util.ArrayList<>();
        pedidos.add(pedidoTest);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setClienteId(11);
        pedido2.setTotalFinal(600000);
        pedido2.setEstadoPedido("PENDIENTE");
        pedidos.add(pedido2);

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // When
        java.util.List<Pedido> resultado = pedidoService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debe haber 2 pedidos");
        assertEquals(10, resultado.get(0).getClienteId());
        assertEquals(11, resultado.get(1).getClienteId());

        verify(pedidoRepository, times(1)).findAll();
    }
}
