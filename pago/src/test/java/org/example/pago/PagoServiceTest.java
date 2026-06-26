package org.example.pago;

import feign.FeignException;
import org.example.pago.client.PedidoClient;
import org.example.pago.dto.PagoRequestDTO;
import org.example.pago.dto.PedidoDTO;
import org.example.pago.model.Pago;
import org.example.pago.repository.PagoRepository;
import org.example.pago.service.PagoService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagoService - Pruebas Unitarias con Feign")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoTest;
    private PagoRequestDTO dtoTest;
    private PedidoDTO pedidoMock;

    @BeforeEach
    void setUp() {
        // Given - Preparar datos de prueba
        pagoTest = new Pago();
        pagoTest.setId(1);
        pagoTest.setPedidoId(100);
        pagoTest.setMontoPagado(150000);
        pagoTest.setMetodoPago("Tarjeta de Crédito");
        pagoTest.setFechaPago(LocalDateTime.now());

        dtoTest = new PagoRequestDTO();
        dtoTest.setPedidoId(100);
        dtoTest.setMontoPagado(150000);
        dtoTest.setMetodoPago("Tarjeta de Crédito");

        pedidoMock = new PedidoDTO();
        pedidoMock.setId(100);
        pedidoMock.setClienteId(1);
        pedidoMock.setTotalFinal(150000);
    }

    @Test
    @DisplayName("Debe crear un pago exitosamente cuando el pedido existe")
    void testGuardarPagoExitosoConPedidoValido() {
        // Given
        when(pedidoClient.obtenerPedidoPorId(100)).thenReturn(pedidoMock);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoTest);

        // When
        Pago resultado = pagoService.save(dtoTest);

        // Then
        assertNotNull(resultado, "El pago no debe ser null");
        assertEquals(100, resultado.getPedidoId(), "El ID del pedido debe ser 100");
        assertEquals(150000, resultado.getMontoPagado(), "El monto debe ser 150000");
        assertEquals("Tarjeta de Crédito", resultado.getMetodoPago());

        verify(pedidoClient, times(1)).obtenerPedidoPorId(100);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el pedido no existe (Feign 404)")
    void testGuardarPagoFallaConPedidoNoExistente() {
        // Given
        when(pedidoClient.obtenerPedidoPorId(999))
                .thenThrow(FeignException.class);

        PagoRequestDTO dtoInvalido = new PagoRequestDTO();
        dtoInvalido.setPedidoId(999);
        dtoInvalido.setMontoPagado(150000);
        dtoInvalido.setMetodoPago("Tarjeta");

        // When - Then
        assertThrows(FeignException.class, () -> pagoService.save(dtoInvalido),
                "Debe lanzar FeignException cuando el pedido no existe");

        verify(pedidoClient, times(1)).obtenerPedidoPorId(999);
        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe obtener un pago por ID cuando existe")
    void testBuscarPagoPorIdExistente() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoTest));

        // When
        Optional<Pago> resultado = pagoService.findById(1);

        // Then
        assertTrue(resultado.isPresent(), "El pago debe existir");
        assertEquals(100, resultado.get().getPedidoId());
        verify(pagoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe actualizar un pago con mismo pedido sin re-validar")
    void testActualizarPagoConMismoPedido() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoTest));

        PagoRequestDTO dtoActualizado = new PagoRequestDTO();
        dtoActualizado.setPedidoId(100); // Mismo ID
        dtoActualizado.setMontoPagado(160000); // Monto diferente
        dtoActualizado.setMetodoPago("Transferencia Bancaria");

        Pago pagoActualizado = new Pago();
        pagoActualizado.setId(1);
        pagoActualizado.setPedidoId(100);
        pagoActualizado.setMontoPagado(160000);
        pagoActualizado.setMetodoPago("Transferencia Bancaria");

        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoActualizado);

        // When
        Pago resultado = pagoService.actualizar(1, dtoActualizado);

        // Then
        assertEquals(160000, resultado.getMontoPagado());
        assertEquals("Transferencia Bancaria", resultado.getMetodoPago());

        // No debe validar el pedido porque el ID no cambió
        verify(pedidoClient, never()).obtenerPedidoPorId(anyInt());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("Debe validar nuevo pedido al cambiar el ID en actualización")
    void testActualizarPagoConNuevoPedido() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoTest));

        PedidoDTO nuevoPedidoMock = new PedidoDTO();
        nuevoPedidoMock.setId(200);
        nuevoPedidoMock.setTotalFinal(200000);

        when(pedidoClient.obtenerPedidoPorId(200)).thenReturn(nuevoPedidoMock);

        PagoRequestDTO dtoActualizado = new PagoRequestDTO();
        dtoActualizado.setPedidoId(200); // ID diferente
        dtoActualizado.setMontoPagado(200000);
        dtoActualizado.setMetodoPago("Efectivo");

        Pago pagoActualizado = new Pago();
        pagoActualizado.setId(1);
        pagoActualizado.setPedidoId(200);
        pagoActualizado.setMontoPagado(200000);
        pagoActualizado.setMetodoPago("Efectivo");

        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoActualizado);

        // When
        Pago resultado = pagoService.actualizar(1, dtoActualizado);

        // Then
        assertEquals(200, resultado.getPedidoId(), "Debe actualizar a nuevo pedido");

        // DEBE validar el nuevo pedido
        verify(pedidoClient, times(1)).obtenerPedidoPorId(200);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("Debe eliminar un pago correctamente")
    void testEliminarPago() {
        // Given
        Integer idPago = 1;

        // When
        pagoService.delete(idPago);

        // Then
        verify(pagoRepository, times(1)).deleteById(idPago);
    }

    @Test
    @DisplayName("Debe verificar existencia de pago correctamente")
    void testVerificarExistenciaPago() {
        // Given
        when(pagoRepository.existsById(1)).thenReturn(true);
        when(pagoRepository.existsById(999)).thenReturn(false);

        // When - Then
        assertTrue(pagoService.existePorId(1));
        assertFalse(pagoService.existePorId(999));
        verify(pagoRepository, times(1)).existsById(1);
        verify(pagoRepository, times(1)).existsById(999);
    }
}