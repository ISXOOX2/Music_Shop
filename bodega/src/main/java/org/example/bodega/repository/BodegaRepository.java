package org.example.bodega.repository;

import org.example.bodega.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Integer> {

    List<Bodega> findByNombreContainingIgnoreCase(String nombre);

    List<Bodega> findBySucursalId(Integer sucursalId);
}