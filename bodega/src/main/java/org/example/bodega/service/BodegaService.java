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
        log.info("Obteniendo lista de todas las bodegas");
        return bodegaRepository.findAll();
    }

    public Optional<Bodega> findById(Integer id) {
        log.info("Buscando bodega con ID: {}", id);
        return bodegaRepository.findById(id);
    }

    public List<Bodega> findByNombre(String nombre) {
        log.info("Buscando bodegas con nombre que contenga: {}", nombre);
        return bodegaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Bodega> findBySucursalId(Integer sucursalId) {
        log.info("Buscando bodegas de la sucursal ID: {}", sucursalId);
        return bodegaRepository.findBySucursalId(sucursalId);
    }

    public Bodega save(Bodega bodega) {
        log.info("Guardando nueva bodega: {}", bodega.getNombre());
        Bodega guardada = bodegaRepository.save(bodega);
        log.info("Bodega guardada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }

    public void delete(Integer id) {
        log.warn("Eliminando bodega con ID: {}", id);
        bodegaRepository.deleteById(id);
        log.info("Bodega con ID: {} eliminada correctamente", id);
    }

    public boolean existsById(Integer id) {
        return bodegaRepository.existsById(id);
    }
}
