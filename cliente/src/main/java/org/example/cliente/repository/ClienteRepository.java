package org.example.cliente.repository;

import org.example.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    Cliente findByRut(String rut);

    Cliente findByEmail(String email);
}
