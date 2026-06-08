package org.example.proveedor.service;

import org.example.proveedor.dto.ProveedorRequestDTO;
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
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findById(Integer id) {
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> findByRazonSocial(String razonSocial) {
        return proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    public Proveedor findByRut(String rut) {
        return proveedorRepository.findByRut(rut);
    }

    public Proveedor findByEmail(String email) {
        return proveedorRepository.findByEmail(email);
    }

    // Recibe DTO para crear
    public Proveedor save(ProveedorRequestDTO dto) {
        log.info("Guardando nuevo proveedor: {}", dto.getRazonSocial());

        Proveedor proveedor = new Proveedor();
        proveedor.setRut(dto.getRut());
        proveedor.setRazonSocial(dto.getRazonSocial());
        proveedor.setEmail(dto.getEmail());
        proveedor.setTelefono(dto.getTelefono());

        return proveedorRepository.save(proveedor);
    }

    //Recibe ID y DTO para actualizar de forma segura
    public Proveedor update(Integer id, ProveedorRequestDTO dto) {
        log.info("Actualizando proveedor con ID: {}", id);

        Proveedor proveedor = new Proveedor();
        proveedor.setId(id); // Forzamos el ID existente
        proveedor.setRut(dto.getRut());
        proveedor.setRazonSocial(dto.getRazonSocial());
        proveedor.setEmail(dto.getEmail());
        proveedor.setTelefono(dto.getTelefono());

        return proveedorRepository.save(proveedor);
    }

    public void delete(Integer id) {
        proveedorRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return proveedorRepository.existsById(id);
    }
}