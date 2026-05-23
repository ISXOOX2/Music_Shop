package org.example.pago.repository;

import org.example.pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Al heredar de JpaRepository ya tenemos listos los métodos básicos de BD
}