package org.example.pago.service;

import org.example.pago.model.Pago;
import org.example.pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> findAll(){
        return pagoRepository.findAll();
    }

    public Optional<Pago> findById(Integer id){
        return pagoRepository.findById(id);
    }

    public Pago save(Pago pago){

        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        }
        return pagoRepository.save(pago);
    }

    public void delete(Integer id){
        pagoRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return pagoRepository.existsById(id);
    }
}