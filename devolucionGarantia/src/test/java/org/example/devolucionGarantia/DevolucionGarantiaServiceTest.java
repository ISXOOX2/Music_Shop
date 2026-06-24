package org.example.devolucionGarantia;

import org.example.devolucionGarantia.client.InventarioClient;
import org.example.devolucionGarantia.client.PedidoClient;
import org.example.devolucionGarantia.dto.DevolucionGarantiaDTO;
import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.repository.DevolucionGarantiaRepository;
import org.example.devolucionGarantia.service.DevolucionGarantiaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DevolucionGarantiaServiceTest")
class DevolucionGarantiaServiceTest {

    @Mock
    private DevolucionGarantiaRepository devolucionRepository;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private DevolucionGarantiaService devolucionService;

    private DevolucionGarantia devolucionTest;
    private DevolucionGarantiaDTO dtoTest;

    @BeforeEach
    void setUp() {
        // Model con todos los campos
        devolucionTest = new DevolucionGarantia();
        devolucionTest.setId(1);
        devolucionTest.setTipoSolicitud("DEVOLUCION");
        devolucionTest.setMotivo("Producto defectuoso");
        devolucionTest.setEstadoResolucion("PROCESADA_Y_ACEPTADA");
        // NOTA: No tiene pedidoId como campo directo, pero lo validamos vía client

        // DTO sin ID
        dtoTest = new DevolucionGarantiaDTO();
        dtoTest.setIdPedido(100);  // ← NOTA: campo del DTO es "idPedido"
        dtoTest.setTipoSolicitud("DEVOLUCION");
        dtoTest.setMotivo("Producto defectuoso");
        dtoTest.setIdInventario(5);
        dtoTest.setCantidad(2);
    }

    @Test
    @DisplayName("Debe registrar solicitud de devolución exitosamente con validación Feign")
    void testRegistrarSolicitudDevolucionExitosa() {
        // Given - Mock del PedidoClient (valida que pedido existe)
        doNothing().when(pedidoClient).obtenerPedidoPorId(100);

        // Mock del InventarioClient (aumenta stock)
        doNothing().when(inventarioClient).aumentarStock(5, 2);

        // Mock del repository para guardar
        when(devolucionRepository.save(any(DevolucionGarantia.class))).thenReturn(devolucionTest);

        // When
        DevolucionGarantia resultado = devolucionService.registrarSolicitud(dtoTest);

        // Then
        assertNotNull(resultado);
        assertEquals("DEVOLUCION", resultado.getTipoSolicitud());
        assertEquals("Producto defectuoso", resultado.getMotivo());
        assertEquals("PROCESADA_Y_ACEPTADA", resultado.getEstadoResolucion());

        // Verificar que se llamaron los clients
        verify(pedidoClient, times(1)).obtenerPedidoPorId(100);
        verify(inventarioClient, times(1)).aumentarStock(5, 2);
        verify(devolucionRepository, times(1)).save(any(DevolucionGarantia.class));
    }

    @Test
    @DisplayName("Debe obtener una devolución por ID existente")
    void testObtenerDevolucionPorIdExistente() {
        // Given
        when(devolucionRepository.findById(1)).thenReturn(Optional.of(devolucionTest));

        // When
        DevolucionGarantia resultado = devolucionService.obtenerPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("DEVOLUCION", resultado.getTipoSolicitud());
        assertEquals("Producto defectuoso", resultado.getMotivo());
        verify(devolucionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando devolución no existe")
    void testObtenerDevolucionPorIdNoExistente() {
        // Given
        when(devolucionRepository.findById(999)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(ResponseStatusException.class, () -> {
            devolucionService.obtenerPorId(999);
        });

        verify(devolucionRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debe actualizar una solicitud de devolución")
    void testActualizarDevolucion() {
        // Given
        DevolucionGarantia devolucionActualizada = new DevolucionGarantia();
        devolucionActualizada.setId(1);
        devolucionActualizada.setTipoSolicitud("GARANTIA");  // Cambio de tipo
        devolucionActualizada.setMotivo("Producto defectuoso - Actualizado");
        devolucionActualizada.setEstadoResolucion("RESUELTA");

        when(devolucionRepository.findById(1)).thenReturn(Optional.of(devolucionTest));
        when(devolucionRepository.save(any(DevolucionGarantia.class))).thenReturn(devolucionActualizada);

        // When
        DevolucionGarantia resultado = devolucionService.actualizar(1, devolucionActualizada);

        // Then
        assertNotNull(resultado);
        assertEquals("GARANTIA", resultado.getTipoSolicitud());
        assertEquals("RESUELTA", resultado.getEstadoResolucion());
        verify(devolucionRepository, times(1)).findById(1);
        verify(devolucionRepository, times(1)).save(any(DevolucionGarantia.class));
    }

    @Test
    @DisplayName("Debe eliminar una devolución exitosamente")
    void testEliminarDevolucion() {
        // Given
        Integer idDevolucion = 1;

        // When
        devolucionService.eliminar(idDevolucion);

        // Then
        verify(devolucionRepository, times(1)).deleteById(idDevolucion);
    }

}