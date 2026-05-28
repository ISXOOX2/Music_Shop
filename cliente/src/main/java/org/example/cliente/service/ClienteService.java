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
        try {
            log.info("Obteniendo lista de todos los clientes");
            return clienteRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de clientes: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de clientes");
        }
    }

    public Optional<Cliente> findById(Integer id) {
        try {
            log.info("Buscando cliente con ID: {}", id);
            Optional<Cliente> resultado = clienteRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún cliente con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar cliente con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el cliente con ID: " + id);
        }
    }

    public List<Cliente> findByNombre(String nombre) {
        try {
            log.info("Buscando clientes con nombre que contenga: {}", nombre);
            return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
        } catch (Exception e) {
            log.error("Error al buscar clientes por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar clientes por nombre: " + nombre);
        }
    }

    public Cliente findByRut(String rut) {
        try {
            log.info("Buscando cliente con RUT: {}", rut);
            Cliente resultado = clienteRepository.findByRut(rut);
            if (resultado == null) {
                log.warn("No se encontró ningún cliente con RUT: {}", rut);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar cliente con RUT {}: {}", rut, e.getMessage());
            throw new RuntimeException("Error al buscar el cliente con RUT: " + rut);
        }
    }

    public Cliente findByEmail(String email) {
        try {
            log.info("Buscando cliente con email: {}", email);
            Cliente resultado = clienteRepository.findByEmail(email);
            if (resultado == null) {
                log.warn("No se encontró ningún cliente con email: {}", email);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar cliente con email {}: {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar el cliente con email: " + email);
        }
    }

    public Cliente save(Cliente cliente) {
        try {
            log.info("Guardando cliente: {}", cliente.getNombreCompleto());
            Cliente guardado = clienteRepository.save(cliente);
            log.info("Cliente guardado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al guardar cliente '{}': {}", cliente.getNombreCompleto(), e.getMessage());
            throw new RuntimeException("No se pudo guardar el cliente: " + cliente.getNombreCompleto());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando cliente con ID: {}", id);
            clienteRepository.deleteById(id);
            log.info("Cliente con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar cliente con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el cliente con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return clienteRepository.existsById(id);
    }
}
