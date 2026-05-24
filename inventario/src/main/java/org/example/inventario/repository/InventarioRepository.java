package org.example.inventario.repository;

import org.example.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    // Hereda de forma automática todos los métodos CRUD básicos
}