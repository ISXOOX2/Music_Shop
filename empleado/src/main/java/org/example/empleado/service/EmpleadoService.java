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

    public List<Empleado> findByNombre(String nombre) {
        return empleadoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Empleado> findBySucursalId(Integer sucursalId) {
        return empleadoRepository.findBySucursalId(sucursalId);
    }

    public Empleado findByCedula(String cedula) {
        return empleadoRepository.findByCedula(cedula);
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
