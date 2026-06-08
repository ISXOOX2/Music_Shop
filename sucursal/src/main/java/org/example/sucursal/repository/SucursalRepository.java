package org.example.sucursal.repository;

import org.example.sucursal.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    List<Sucursal> findByNombreContainingIgnoreCase(String nombre);
}
