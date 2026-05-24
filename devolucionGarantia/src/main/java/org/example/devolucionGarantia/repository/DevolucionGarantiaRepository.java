package org.example.devolucionGarantia.repository;

import org.example.devolucionGarantia.model.DevolucionGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionGarantiaRepository extends JpaRepository<DevolucionGarantia, Integer> {
}