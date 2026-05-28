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
        try {
            log.info("Obteniendo lista de todos los proveedores");
            return proveedorRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de proveedores: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de proveedores");
        }
    }

    public Optional<Proveedor> findById(Integer id) {
        try {
            log.info("Buscando proveedor con ID: {}", id);
            Optional<Proveedor> resultado = proveedorRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún proveedor con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar proveedor con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el proveedor con ID: " + id);
        }
    }

    public List<Proveedor> findByRazonSocial(String razonSocial) {
        try {
            log.info("Buscando proveedores con razón social que contenga: {}", razonSocial);
            return proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
        } catch (Exception e) {
            log.error("Error al buscar proveedores por razón social '{}': {}", razonSocial, e.getMessage());
            throw new RuntimeException("Error al buscar proveedores por razón social: " + razonSocial);
        }
    }

    public Proveedor findByRut(String rut) {
        try {
            log.info("Buscando proveedor con RUT: {}", rut);
            Proveedor resultado = proveedorRepository.findByRut(rut);
            if (resultado == null) {
                log.warn("No se encontró ningún proveedor con RUT: {}", rut);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar proveedor con RUT {}: {}", rut, e.getMessage());
            throw new RuntimeException("Error al buscar el proveedor con RUT: " + rut);
        }
    }

    public Proveedor findByEmail(String email) {
        try {
            log.info("Buscando proveedor con email: {}", email);
            Proveedor resultado = proveedorRepository.findByEmail(email);
            if (resultado == null) {
                log.warn("No se encontró ningún proveedor con email: {}", email);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar proveedor con email {}: {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar el proveedor con email: " + email);
        }
    }

    public Proveedor save(Proveedor proveedor) {
        try {
            log.info("Guardando proveedor: {}", proveedor.getRazonSocial());
            Proveedor guardado = proveedorRepository.save(proveedor);
            log.info("Proveedor guardado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al guardar proveedor '{}': {}", proveedor.getRazonSocial(), e.getMessage());
            throw new RuntimeException("No se pudo guardar el proveedor: " + proveedor.getRazonSocial());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando proveedor con ID: {}", id);
            proveedorRepository.deleteById(id);
            log.info("Proveedor con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar proveedor con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el proveedor con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return proveedorRepository.existsById(id);
    }
}