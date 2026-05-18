package org.example.empleado.repository;

import org.example.empleado.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    List<Empleado> findBySucursalId(Integer sucursalId);

    Empleado findByCedula(String cedula);
}
