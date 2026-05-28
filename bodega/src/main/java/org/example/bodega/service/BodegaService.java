package org.example.bodega.service;

import org.example.bodega.model.Bodega;
import org.example.bodega.repository.BodegaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaService {

    private static final Logger log = LoggerFactory.getLogger(BodegaService.class);

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll() {
        try {
            log.info("Obteniendo lista de todas las bodegas");
            return bodegaRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de bodegas: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de bodegas");
        }
    }

    public Optional<Bodega> findById(Integer id) {
        try {
            log.info("Buscando bodega con ID: {}", id);
            Optional<Bodega> resultado = bodegaRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ninguna bodega con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar bodega con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la bodega con ID: " + id);
        }
    }

    public List<Bodega> findByNombre(String nombre) {
        try {
            log.info("Buscando bodegas con nombre que contenga: {}", nombre);
            return bodegaRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            log.error("Error al buscar bodegas por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar bodegas por nombre: " + nombre);
        }
    }

    public List<Bodega> findBySucursalId(Integer sucursalId) {
        try {
            log.info("Buscando bodegas de la sucursal ID: {}", sucursalId);
            return bodegaRepository.findBySucursalId(sucursalId);
        } catch (Exception e) {
            log.error("Error al buscar bodegas de sucursal ID {}: {}", sucursalId, e.getMessage());
            throw new RuntimeException("Error al buscar bodegas de la sucursal ID: " + sucursalId);
        }
    }

    public Bodega save(Bodega bodega) {
        try {
            log.info("Guardando nueva bodega: {}", bodega.getNombre());
            Bodega guardada = bodegaRepository.save(bodega);
            log.info("Bodega guardada exitosamente con ID: {}", guardada.getId());
            return guardada;
        } catch (Exception e) {
            log.error("Error al guardar la bodega '{}': {}", bodega.getNombre(), e.getMessage());
            throw new RuntimeException("No se pudo guardar la bodega: " + bodega.getNombre());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando bodega con ID: {}", id);
            bodegaRepository.deleteById(id);
            log.info("Bodega con ID: {} eliminada correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar bodega con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar la bodega con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return bodegaRepository.existsById(id);
    }
}
