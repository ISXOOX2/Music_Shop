package org.example.sucursal.service;

import org.example.sucursal.model.Sucursal;
import org.example.sucursal.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> findById(Integer id) {
        return sucursalRepository.findById(id);
    }

    public List<Sucursal> findByNombre(String nombre) {
        return sucursalRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void delete(Integer id) {
        sucursalRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return sucursalRepository.existsById(id);
    }
}
