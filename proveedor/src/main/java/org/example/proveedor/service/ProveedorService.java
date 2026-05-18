package org.example.proveedor.service;

import org.example.proveedor.model.Proveedor;
import org.example.proveedor.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findById(Integer id) {
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> findByNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Proveedor> findByPais(String pais) {
        return proveedorRepository.findByPais(pais);
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void delete(Integer id) {
        proveedorRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return proveedorRepository.existsById(id);
    }
}
