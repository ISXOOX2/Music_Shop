package org.example.producto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.producto.dto.ProductoRequestDTO;
import org.example.producto.model.Producto;
import org.example.producto.repository.ProductoRepository;
import org.example.producto.service.ProductoService;
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

@NoArgsConstructor
@Data
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductoService - Pruebas Unitarias")
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto productoTest;
    private ProductoRequestDTO dtoTest;

    @BeforeEach
    void setUp() {
        // Given - Preparar datos de prueba
        productoTest = new Producto();
        productoTest.setId(1);
        productoTest.setNombre("Guitarra Acústica");
        productoTest.setDescripcion("Guitarra de madera de alta calidad");
        productoTest.setPrecio(150000);
        productoTest.setCategoria("Cuerdas");
        productoTest.setMarca("Yamaha");

        dtoTest = new ProductoRequestDTO();
        dtoTest.setNombre("Guitarra Acústica");
        dtoTest.setDescripcion("Guitarra de madera de alta calidad");
        dtoTest.setPrecio(150000);
        dtoTest.setCategoria("Cuerdas");
        dtoTest.setMarca("Yamaha");
    }

    @Test
    @DisplayName("Debe crear un producto exitosamente cuando los datos son válidos")
    void testGuardarProductoExitoso() {
        // Given - Datos preparados en setUp()
        when(productoRepository.save(any(Producto.class))).thenReturn(productoTest);

        // When - Ejecutar la acción
        Producto resultado = productoService.save(dtoTest);

        // Then - Validar resultado
        assertNotNull(resultado, "El producto no debe ser null");
        assertEquals("Guitarra Acústica", resultado.getNombre(), "El nombre debe coincidir");
        assertEquals(150000, resultado.getPrecio(), "El precio debe ser 150000");
        assertEquals("Yamaha", resultado.getMarca(), "La marca debe ser Yamaha");

        // Verificar que se llamó al repository
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Debe obtener un producto por ID existente")
    void testBuscarProductoPorIdExistente() {
        // Given
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoTest));

        // When
        Optional<Producto> resultado = productoService.findById(1);

        // Then
        assertTrue(resultado.isPresent(), "El Optional debe contener un producto");
        assertEquals("Guitarra Acústica", resultado.get().getNombre());
        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar Optional vacío cuando el ID no existe")
    void testBuscarProductoPorIdNoExistente() {
        // Given
        when(productoRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Producto> resultado = productoService.findById(999);

        // Then
        assertFalse(resultado.isPresent(), "El Optional debe estar vacío");
        verify(productoRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debe obtener todos los productos")
    void testObtenerTodosLosProductos() {
        // Given
        List<Producto> productos = new ArrayList<>();
        productos.add(productoTest);

        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("Bajo Eléctrico");
        producto2.setPrecio(200000);
        productos.add(producto2);

        when(productoRepository.findAll()).thenReturn(productos);

        // When
        List<Producto> resultado = productoService.findAll();

        // Then
        assertNotNull(resultado, "La lista no debe ser null");
        assertEquals(2, resultado.size(), "Debe haber 2 productos");
        assertEquals("Guitarra Acústica", resultado.get(0).getNombre());
        assertEquals("Bajo Eléctrico", resultado.get(1).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe actualizar un producto existente correctamente")
    void testActualizarProducto() {
        // Given
        Integer idProducto = 1;

        Producto productoActualizado = new Producto();
        productoActualizado.setId(1);
        productoActualizado.setNombre("Guitarra Acústica Premium");
        productoActualizado.setDescripcion("Guitarra de madera de alta calidad mejorada");
        productoActualizado.setPrecio(180000);
        productoActualizado.setCategoria("Cuerdas");
        productoActualizado.setMarca("Yamaha");

        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        ProductoRequestDTO dtoActualizado = new ProductoRequestDTO();
        dtoActualizado.setNombre("Guitarra Acústica Premium");
        dtoActualizado.setDescripcion("Guitarra de madera de alta calidad mejorada");
        dtoActualizado.setPrecio(180000);
        dtoActualizado.setCategoria("Cuerdas");
        dtoActualizado.setMarca("Yamaha");

        // When
        Producto resultado = productoService.update(idProducto, dtoActualizado);

        // Then
        assertEquals("Guitarra Acústica Premium", resultado.getNombre(), "El nombre debe actualizarse");
        assertEquals(180000, resultado.getPrecio(), "El precio debe actualizarse a 180000");
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Debe eliminar un producto existente")
    void testEliminarProducto() {
        // Given
        Integer idProducto = 1;

        // When
        productoService.delete(idProducto);

        // Then
        verify(productoRepository, times(1)).deleteById(idProducto);
    }

    @Test
    @DisplayName("Debe verificar si un producto existe")
    void testVerificarExistenciaProducto() {
        // Given
        when(productoRepository.existsById(1)).thenReturn(true);
        when(productoRepository.existsById(999)).thenReturn(false);

        // When - Then
        assertTrue(productoService.existsById(1), "El producto con ID 1 debe existir");
        assertFalse(productoService.existsById(999), "El producto con ID 999 no debe existir");
        verify(productoRepository, times(1)).existsById(1);
        verify(productoRepository, times(1)).existsById(999);
    }

    @Test
    @DisplayName("Debe buscar productos por categoría")
    void testBuscarProductosPorCategoria() {
        // Given
        List<Producto> productos = new ArrayList<>();
        productos.add(productoTest);

        when(productoRepository.findByCategoria("Cuerdas")).thenReturn(productos);

        // When
        List<Producto> resultado = productoService.findByCategoria("Cuerdas");

        // Then
        assertNotNull(resultado, "La lista no debe ser null");
        assertEquals(1, resultado.size(), "Debe haber 1 producto");
        assertEquals("Cuerdas", resultado.get(0).getCategoria());
        verify(productoRepository, times(1)).findByCategoria("Cuerdas");
    }
}