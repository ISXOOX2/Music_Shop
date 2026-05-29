package org.example.bodega.service;

import org.example.bodega.client.SucursalClient;
import org.example.bodega.dto.BodegaRequestDTO;
import org.example.bodega.model.Bodega;
import org.example.bodega.repository.BodegaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaService {

    private static final Logger log = LoggerFactory.getLogger(BodegaService.class);

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private SucursalClient sucursalClient;

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

    public Bodega save(BodegaRequestDTO dto) {
        log.info("Intentando guardar nueva bodega: {}", dto.getNombre());

        validarSucursal(dto.getSucursalId());

        Bodega bodega = new Bodega();
        bodega.setSucursalId(dto.getSucursalId());
        bodega.setNombre(dto.getNombre());
        bodega.setCapacidadMaxima(dto.getCapacidadMaxima());

        Bodega guardada = bodegaRepository.save(bodega);
        log.info("Bodega guardada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }

    public Bodega update(Integer id, BodegaRequestDTO dto) {
        log.info("Intentando actualizar bodega con ID: {}", id);

        validarSucursal(dto.getSucursalId());

        Bodega bodega = new Bodega();
        bodega.setId(id);
        bodega.setSucursalId(dto.getSucursalId());
        bodega.setNombre(dto.getNombre());
        bodega.setCapacidadMaxima(dto.getCapacidadMaxima());

        Bodega actualizada = bodegaRepository.save(bodega);
        log.info("Bodega con ID: {} actualizada exitosamente", actualizada.getId());
        return actualizada;
    }

    public void delete(Integer id) {
        log.warn("Eliminando bodega con ID: {}", id);
        bodegaRepository.deleteById(id);
        log.info("Bodega con ID: {} eliminada correctamente", id);
    }

    public boolean existsById(Integer id) {
        return bodegaRepository.existsById(id);
    }

    private void validarSucursal(Integer sucursalId) {
        if (!sucursalClient.existeSucursalPorId(sucursalId)) {
            log.error("Error: La sucursal con ID {} no existe.", sucursalId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sucursal especificada no existe.");
        }
    }
}