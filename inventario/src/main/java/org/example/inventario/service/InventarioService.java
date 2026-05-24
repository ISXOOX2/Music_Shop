package org.example.inventario.service;

import org.example.inventario.model.Inventario;
import org.example.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    // Obtener el stock actual de un producto
    public Inventario obtenerPorId(Integer id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el inventario con ID: " + id));
    }

    // Reducir Stock (Llamado de Pedidos al comprar)
    @Transactional
    public Inventario reducirStock(Integer id, Integer cantidadAReducir) {
        Inventario inventario = obtenerPorId(id);

        if (inventario.getCantidad() < cantidadAReducir) {
            throw new RuntimeException("Stock insuficiente. Cantidad disponible: " + inventario.getCantidad() + ", Solicitada: " + cantidadAReducir);
        }

        inventario.setCantidad(inventario.getCantidad() - cantidadAReducir);
        return inventarioRepository.save(inventario);
    }

    // Aumentar Stock (Llamado por Devoluciones/Garantías)
    @Transactional
    public Inventario aumentarStock(Integer id, Integer cantidadAAumentar) {
        Inventario inventario = obtenerPorId(id);

        inventario.setCantidad(inventario.getCantidad() + cantidadAAumentar);
        return inventarioRepository.save(inventario);
    }
}