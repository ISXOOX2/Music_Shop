package org.example.cliente.service;

import org.example.cliente.model.Cliente;
import org.example.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findByNombre(String nombre) {
        return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }

    public Cliente findByRut(String rut) {
        return clienteRepository.findByRut(rut);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return clienteRepository.existsById(id);
    }
}
