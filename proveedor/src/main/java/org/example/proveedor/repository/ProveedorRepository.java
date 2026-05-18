package org.example.proveedor.repository;

import org.example.proveedor.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);

    List<Proveedor> findByPais(String pais);
}
