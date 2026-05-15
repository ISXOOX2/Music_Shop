package com.example.Music_Shop.service;

import com.example.Music_Shop.model.Bodega;
import com.example.Music_Shop.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll(){
        return bodegaRepository.findAll();
    }

    public Optional<Bodega> findById(Integer id){
        return bodegaRepository.findById(id);
    }

    public Bodega save(Bodega bodega){
        return bodegaRepository.save(bodega);
    }

    public void delete(Integer id){
        bodegaRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return bodegaRepository.existsById(id);
    }
}
