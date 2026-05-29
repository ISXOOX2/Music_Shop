package org.example.sucursal.service;

import org.example.sucursal.dto.SucursalRequestDTO;
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

    //Recibe DTO para crear de forma segura
    public Sucursal save(SucursalRequestDTO dto) {
        log.info("Guardando nueva sucursal: {}", dto.getNombre());

        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());

        Sucursal guardada = sucursalRepository.save(sucursal);
        log.info("Sucursal guardada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }

    //Metodo update para recibir el ID y el DTO
    public Sucursal update(Integer id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal con ID: {}", id);

        Sucursal sucursal = new Sucursal();
        sucursal.setId(id);
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());

        Sucursal actualizada = sucursalRepository.save(sucursal);
        log.info("Sucursal con ID: {} actualizada correctamente", actualizada.getId());
        return actualizada;
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