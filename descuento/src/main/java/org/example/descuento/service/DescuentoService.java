package org.example.descuento.service;

import org.example.descuento.model.Descuento;
import org.example.descuento.repository.DescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;

    public List<Descuento> findAll() {
        return descuentoRepository.findAll();
    }

    public Optional<Descuento> findById(Integer id) {
        return descuentoRepository.findById(id);
    }

    public Descuento findByCodigo(String codigo) {
        return descuentoRepository.findByCodigo(codigo);
    }

    public List<Descuento> findByPorcentaje(Double porcentaje) {
        return descuentoRepository.findByPorcentaje(porcentaje);
    }

    public Descuento save(Descuento descuento) {
        return descuentoRepository.save(descuento);
    }

    public void delete(Integer id) {
        descuentoRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return descuentoRepository.existsById(id);
    }
}
