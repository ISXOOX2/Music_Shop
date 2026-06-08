package org.example.descuento.repository;

import org.example.descuento.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Integer> {

    Descuento findByCodigo(String codigo);

    List<Descuento> findByPorcentaje(Double porcentaje);
}