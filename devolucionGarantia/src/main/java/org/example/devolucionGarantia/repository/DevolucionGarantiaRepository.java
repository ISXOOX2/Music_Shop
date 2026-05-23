package org.example.devolucionGarantia.repository;

import org.example.DevolucionGarantia.model.DevolucionGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionGarantiaRepository extends JpaRepository<DevolucionGarantia, Integer> {
    // Todos los métodos de la base de datos están listos gracias a JpaRepository
}