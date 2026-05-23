package org.example.inventario.service;

import org.example.inventario.model.Inventario;
import org.example.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> findAll(){
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> findById(Integer id){
        return inventarioRepository.findById(id);
    }

    public Inventario save(Inventario inventario){
        return inventarioRepository.save(inventario);
    }

    public void delete(Integer id){
        inventarioRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return inventarioRepository.existsById(id);
    }
}