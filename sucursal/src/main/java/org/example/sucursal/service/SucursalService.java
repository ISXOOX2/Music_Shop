package org.example.sucursal.service;

import org.example.sucursal.model.Sucursal;
import org.example.sucursal.repository.SucursalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    private static final Logger log = LoggerFactory.getLogger(SucursalService.class);

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        try {
            log.info("Obteniendo lista de todas las sucursales");
            return sucursalRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de sucursales: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de sucursales");
        }
    }

    public Optional<Sucursal> findById(Integer id) {
        try {
            log.info("Buscando sucursal con ID: {}", id);
            Optional<Sucursal> resultado = sucursalRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ninguna sucursal con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar sucursal con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la sucursal con ID: " + id);
        }
    }

    public List<Sucursal> findByNombre(String nombre) {
        try {
            log.info("Buscando sucursales con nombre que contenga: {}", nombre);
            return sucursalRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            log.error("Error al buscar sucursales por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar sucursales por nombre: " + nombre);
        }
    }

    public Sucursal save(Sucursal sucursal) {
        try {
            log.info("Guardando sucursal: {}", sucursal.getNombre());
            Sucursal guardada = sucursalRepository.save(sucursal);
            log.info("Sucursal guardada exitosamente con ID: {}", guardada.getId());
            return guardada;
        } catch (Exception e) {
            log.error("Error al guardar la sucursal '{}': {}", sucursal.getNombre(), e.getMessage());
            throw new RuntimeException("No se pudo guardar la sucursal: " + sucursal.getNombre());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando sucursal con ID: {}", id);
            sucursalRepository.deleteById(id);
            log.info("Sucursal con ID: {} eliminada correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar sucursal con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar la sucursal con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return sucursalRepository.existsById(id);
    }
}
