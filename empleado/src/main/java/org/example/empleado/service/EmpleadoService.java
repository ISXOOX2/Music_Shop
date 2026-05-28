package org.example.empleado.service;

import org.example.empleado.model.Empleado;
import org.example.empleado.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        try {
            log.info("Obteniendo lista de todos los empleados");
            return empleadoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de empleados: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de empleados");
        }
    }

    public Optional<Empleado> findById(Integer id) {
        try {
            log.info("Buscando empleado con ID: {}", id);
            Optional<Empleado> resultado = empleadoRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún empleado con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar empleado con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el empleado con ID: " + id);
        }
    }

    public List<Empleado> findByNombreCompleto(String nombreCompleto) {
        try {
            log.info("Buscando empleados con nombre que contenga: {}", nombreCompleto);
            return empleadoRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto);
        } catch (Exception e) {
            log.error("Error al buscar empleados por nombre '{}': {}", nombreCompleto, e.getMessage());
            throw new RuntimeException("Error al buscar empleados por nombre: " + nombreCompleto);
        }
    }

    public List<Empleado> findByCargo(String cargo) {
        try {
            log.info("Buscando empleados con cargo: {}", cargo);
            return empleadoRepository.findByCargo(cargo);
        } catch (Exception e) {
            log.error("Error al buscar empleados con cargo '{}': {}", cargo, e.getMessage());
            throw new RuntimeException("Error al buscar empleados con cargo: " + cargo);
        }
    }

    public Empleado findByRut(String rut) {
        try {
            log.info("Buscando empleado con RUT: {}", rut);
            Empleado resultado = empleadoRepository.findByRut(rut);
            if (resultado == null) {
                log.warn("No se encontró ningún empleado con RUT: {}", rut);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar empleado con RUT {}: {}", rut, e.getMessage());
            throw new RuntimeException("Error al buscar el empleado con RUT: " + rut);
        }
    }

    public Empleado save(Empleado empleado) {
        try {
            log.info("Guardando empleado: {}", empleado.getNombreCompleto());
            Empleado guardado = empleadoRepository.save(empleado);
            log.info("Empleado guardado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al guardar empleado '{}': {}", empleado.getNombreCompleto(), e.getMessage());
            throw new RuntimeException("No se pudo guardar el empleado: " + empleado.getNombreCompleto());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando empleado con ID: {}", id);
            empleadoRepository.deleteById(id);
            log.info("Empleado con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar empleado con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el empleado con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return empleadoRepository.existsById(id);
    }
}