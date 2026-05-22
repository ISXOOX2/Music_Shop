package org.example.bodega.service;

import org.example.bodega.model.Bodega;
import org.example.bodega.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll() {
        return bodegaRepository.findAll();
    }

    public Optional<Bodega> findById(Integer id) {
        return bodegaRepository.findById(id);
    }

    public List<Bodega> findByNombre(String nombre) {
        return bodegaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Bodega> findBySucursalId(Integer sucursalId) {
        return bodegaRepository.findBySucursalId(sucursalId);
    }

    public Bodega save(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public void delete(Integer id) {
        bodegaRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return bodegaRepository.existsById(id);
    }
}
