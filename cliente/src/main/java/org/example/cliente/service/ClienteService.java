package org.example.cliente.service;

import org.example.cliente.model.Cliente;
import org.example.cliente.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        log.info("Obteniendo lista de todos los clientes");
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Integer id) {
        log.info("Buscando cliente con ID: {}", id);
        return clienteRepository.findById(id);
    }

    public List<Cliente> findByNombre(String nombre) {
        log.info("Buscando clientes con nombre que contenga: {}", nombre);
        return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }

    public Cliente findByRut(String rut) {
        log.info("Buscando cliente con RUT: {}", rut);
        return clienteRepository.findByRut(rut);
    }

    public Cliente findByEmail(String email) {
        log.info("Buscando cliente con email: {}", email);
        return clienteRepository.findByEmail(email);
    }

    public Cliente save(Cliente cliente) {
        log.info("Guardando cliente: {}", cliente.getNombreCompleto());
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente guardado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public void delete(Integer id) {
        log.warn("Eliminando cliente con ID: {}", id);
        clienteRepository.deleteById(id);
        log.info("Cliente con ID: {} eliminado correctamente", id);
    }

    public boolean existsById(Integer id) {
        return clienteRepository.existsById(id);
    }
}
