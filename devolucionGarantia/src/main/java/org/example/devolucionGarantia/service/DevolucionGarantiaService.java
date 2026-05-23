package org.example.devolucionGarantia.service;

import org.example.DevolucionGarantia.model.DevolucionGarantia;
import org.example.devolucionGarantia.repository.DevolucionGarantiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DevolucionGarantiaService {

    @Autowired
    private DevolucionGarantiaRepository devolucionGarantiaRepository;

    public List<DevolucionGarantia> findAll(){
        return devolucionGarantiaRepository.findAll();
    }

    public Optional<DevolucionGarantia> findById(Integer id){
        return devolucionGarantiaRepository.findById(id);
    }

    public DevolucionGarantia save(DevolucionGarantia devolucionGarantia){
        // Pequeña lógica de negocio: Si no mandan estado, se pone "EN REVISION" por defecto
        if (devolucionGarantia.getEstadoResolucion() == null || devolucionGarantia.getEstadoResolucion().isEmpty()) {
            devolucionGarantia.setEstadoResolucion("EN REVISION");
        }
        return devolucionGarantiaRepository.save(devolucionGarantia);
    }

    public void delete(Integer id){
        devolucionGarantiaRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return devolucionGarantiaRepository.existsById(id);
    }
}