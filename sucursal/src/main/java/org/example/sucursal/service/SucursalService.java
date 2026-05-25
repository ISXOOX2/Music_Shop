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
        log.info("Obteniendo lista de todas las sucursales");
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> findById(Integer id) {
        log.info("Buscando sucursal con ID: {}", id);
        return sucursalRepository.findById(id);
    }

    public List<Sucursal> findByNombre(String nombre) {
        log.info("Buscando sucursales con nombre que contenga: {}", nombre);
        return sucursalRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Sucursal save(Sucursal sucursal) {
        log.info("Guardando sucursal: {}", sucursal.getNombre());
        Sucursal guardada = sucursalRepository.save(sucursal);
        log.info("Sucursal guardada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }

    public void delete(Integer id) {
        log.warn("Eliminando sucursal con ID: {}", id);
        sucursalRepository.deleteById(id);
        log.info("Sucursal con ID: {} eliminada correctamente", id);
    }

    public boolean existsById(Integer id) {
        return sucursalRepository.existsById(id);
    }
}
