package org.example.empleado.service;

import org.example.empleado.model.Empleado;
import org.example.empleado.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> findById(Integer id) {
        return empleadoRepository.findById(id);
    }

    public List<Empleado> findByNombreCompleto(String nombreCompleto) {
        return empleadoRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto);
    }

    public List<Empleado> findByCargo(String cargo) {
        return empleadoRepository.findByCargo(cargo);
    }

    public Empleado findByRut(String rut) {
        return empleadoRepository.findByRut(rut);
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public void delete(Integer id) {
        empleadoRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return empleadoRepository.existsById(id);
    }
}
