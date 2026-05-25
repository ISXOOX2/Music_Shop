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
        log.info("Obteniendo lista de todos los empleados");
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> findById(Integer id) {
        log.info("Buscando empleado con ID: {}", id);
        return empleadoRepository.findById(id);
    }

    public List<Empleado> findByNombreCompleto(String nombreCompleto) {
        log.info("Buscando empleados con nombre que contenga: {}", nombreCompleto);
        return empleadoRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto);
    }

    public List<Empleado> findByCargo(String cargo) {
        log.info("Buscando empleados con cargo: {}", cargo);
        return empleadoRepository.findByCargo(cargo);
    }

    public Empleado findByRut(String rut) {
        log.info("Buscando empleado con RUT: {}", rut);
        return empleadoRepository.findByRut(rut);
    }

    public Empleado save(Empleado empleado) {
        log.info("Guardando empleado: {}", empleado.getNombreCompleto());
        Empleado guardado = empleadoRepository.save(empleado);
        log.info("Empleado guardado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public void delete(Integer id) {
        log.warn("Eliminando empleado con ID: {}", id);
        empleadoRepository.deleteById(id);
        log.info("Empleado con ID: {} eliminado correctamente", id);
    }

    public boolean existsById(Integer id) {
        return empleadoRepository.existsById(id);
    }
}
