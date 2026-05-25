package org.example.proveedor.service;

import org.example.proveedor.model.Proveedor;
import org.example.proveedor.repository.ProveedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private static final Logger log = LoggerFactory.getLogger(ProveedorService.class);

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        log.info("Obteniendo lista de todos los proveedores");
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findById(Integer id) {
        log.info("Buscando proveedor con ID: {}", id);
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> findByRazonSocial(String razonSocial) {
        log.info("Buscando proveedores con razón social que contenga: {}", razonSocial);
        return proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    public Proveedor findByRut(String rut) {
        log.info("Buscando proveedor con RUT: {}", rut);
        return proveedorRepository.findByRut(rut);
    }

    public Proveedor findByEmail(String email) {
        log.info("Buscando proveedor con email: {}", email);
        return proveedorRepository.findByEmail(email);
    }

    public Proveedor save(Proveedor proveedor) {
        log.info("Guardando proveedor: {}", proveedor.getRazonSocial());
        Proveedor guardado = proveedorRepository.save(proveedor);
        log.info("Proveedor guardado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public void delete(Integer id) {
        log.warn("Eliminando proveedor con ID: {}", id);
        proveedorRepository.deleteById(id);
        log.info("Proveedor con ID: {} eliminado correctamente", id);
    }

    public boolean existsById(Integer id) {
        return proveedorRepository.existsById(id);
    }
}