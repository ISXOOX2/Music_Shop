package org.example.inventario;

import org.example.inventario.client.ProductoClient;
import org.example.inventario.client.SucursalClient;
import org.example.inventario.dto.InventarioRequestDTO;
import org.example.inventario.model.Inventario;
import org.example.inventario.repository.InventarioRepository;
import org.example.inventario.service.InventarioService;
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
@DisplayName("InventarioService - Pruebas Unitarias")
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private SucursalClient sucursalClient;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventarioTest;
    private InventarioRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        // Given - Preparar datos de prueba
        inventarioTest = new Inventario();
        inventarioTest.setId(1);
        inventarioTest.setProductoId(100);
        inventarioTest.setSucursalId(50);
        inventarioTest.setCantidadDisponible(25);
        inventarioTest.setCantidadReservada(5);

        dtoTest = new InventarioRequestDTO();
        dtoTest.setProductoId(100);
        dtoTest.setSucursalId(50);
        dtoTest.setCantidadDisponible(25);
        dtoTest.setCantidadReservada(5);
    }

    @Test
    @DisplayName("Debe crear un inventario validando existencia de producto y sucursal")
    void testGuardarInventarioConValidacionExitosa() {
        // Given
        when(productoClient.existeProductoPorId(100)).thenReturn(true);
        when(sucursalClient.existeSucursalPorId(50)).thenReturn(true);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioTest);

        // When
        Inventario resultado = inventarioService.save(dtoTest);

        // Then
        assertNotNull(resultado, "El inventario no debe ser null");
        assertEquals(100, resultado.getProductoId());
        assertEquals(50, resultado.getSucursalId());
        assertEquals(25, resultado.getCantidadDisponible());
        assertEquals(5, resultado.getCantidadReservada());

        verify(productoClient, times(1)).existeProductoPorId(100);
        verify(sucursalClient, times(1)).existeSucursalPorId(50);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe fallar cuando el producto no existe")
    void testGuardarInventarioConProductoInexistente() {

        when(productoClient.existeProductoPorId(999)).thenReturn(false);

        InventarioRequestDTO dtoInvalido = new InventarioRequestDTO();
        dtoInvalido.setProductoId(999);
        dtoInvalido.setSucursalId(50);
        dtoInvalido.setCantidadDisponible(25);


        assertThrows(Exception.class, () -> inventarioService.save(dtoInvalido),
                "Debe fallar cuando el producto no existe");

        verify(productoClient, times(1)).existeProductoPorId(999);
        verify(inventarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe obtener un inventario por ID cuando existe")
    void testBuscarInventarioPorIdExistente() {

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioTest));


        Optional<Inventario> resultado = inventarioService.findById(1);


        assertTrue(resultado.isPresent(), "El inventario debe existir");
        assertEquals(100, resultado.get().getProductoId());
        verify(inventarioRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe descontar cantidad del inventario cuando hay stock disponible")
    void testDescontarDelInventario() {

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioTest));

        Inventario inventarioActualizado = new Inventario();
        inventarioActualizado.setId(1);
        inventarioActualizado.setProductoId(100);
        inventarioActualizado.setSucursalId(50);
        inventarioActualizado.setCantidadDisponible(20); // 25 - 5
        inventarioActualizado.setCantidadReservada(5);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioActualizado);

        // When
        Inventario resultado = inventarioService.descontar(1, 5);

        // Then
        assertEquals(20, resultado.getCantidadDisponible(), "Debe descontar 5 unidades");
        verify(inventarioRepository, times(1)).findById(1);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe fallar al descontar cantidad mayor al stock disponible")
    void testDescontarMasDelStockDisponible() {
        // Given - El inventario tiene 25 disponibles, intentamos descontar 30
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioTest));

        // When - Then
        assertThrows(Exception.class, () -> inventarioService.descontar(1, 30),
                "Debe fallar cuando se descuenta más que lo disponible");

        verify(inventarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe verificar si hay stock disponible")
    void testVerificarStockDisponible() {
        // Given
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioTest));

        // When - Then
        assertTrue(inventarioService.verificarStock(1, 10),
                "Debe retornar true cuando hay 10 o menos (disponibles = 25)");
        assertTrue(inventarioService.verificarStock(1, 25),
                "Debe retornar true cuando exactamente hay 25");
        assertFalse(inventarioService.verificarStock(1, 30),
                "Debe retornar false cuando se solicita más de 25");
    }

    @Test
    @DisplayName("Debe revertir una reserva correctamente")
    void testRevertirReserva() {
        // Given
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioTest));

        Inventario inventarioActualizado = new Inventario();
        inventarioActualizado.setId(1);
        inventarioActualizado.setProductoId(100);
        inventarioActualizado.setCantidadDisponible(30); // 25 + 5 (revertidas)
        inventarioActualizado.setCantidadDisponible(30);
        inventarioActualizado.setCantidadReservada(0);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioActualizado);

        // When
        Inventario resultado = inventarioService.revertirReserva(1, 5);

        // Then
        assertEquals(0, resultado.getCantidadReservada(), "La reserva debe ser 0");
        assertEquals(30, resultado.getCantidadDisponible());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }
}