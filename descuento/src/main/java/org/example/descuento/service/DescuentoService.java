package org.example.descuento.service;

import org.example.descuento.dto.DescuentoRequestDTO;
import org.example.descuento.model.Descuento;
import org.example.descuento.repository.DescuentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescuentoService {

    private static final Logger log = LoggerFactory.getLogger(DescuentoService.class);

    @Autowired
    private DescuentoRepository descuentoRepository;

    public List<Descuento> findAll() {
        log.info("Obteniendo lista de todos los descuentos");
        return descuentoRepository.findAll();
    }

    public Optional<Descuento> findById(Integer id) {
        log.info("Buscando descuento con ID: {}", id);
        return descuentoRepository.findById(id);
    }

    public Descuento findByCodigo(String codigo) {
        log.info("Buscando descuento con código: {}", codigo);
        return descuentoRepository.findByCodigo(codigo);
    }

    public List<Descuento> findByPorcentaje(Double porcentaje) {
        log.info("Buscando descuentos con porcentaje: {}", porcentaje);
        return descuentoRepository.findByPorcentaje(porcentaje);
    }

    //Recibe DTO para crear
    public Descuento save(DescuentoRequestDTO dto) {
        log.info("Guardando descuento con código: {}", dto.getCodigo());

        Descuento descuento = new Descuento();
        descuento.setCodigo(dto.getCodigo());
        descuento.setPorcentaje(dto.getPorcentaje());
        descuento.setFechaExpiracion(dto.getFechaExpiracion());

        Descuento guardado = descuentoRepository.save(descuento);
        log.info("Descuento guardado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    //Recibe ID y DTO para actualizar
    public Descuento update(Integer id, DescuentoRequestDTO dto) {
        log.info("Actualizando descuento con ID: {}", id);

        Descuento descuento = new Descuento();
        descuento.setId(id); // Forzamos el ID existente
        descuento.setCodigo(dto.getCodigo());
        descuento.setPorcentaje(dto.getPorcentaje());
        descuento.setFechaExpiracion(dto.getFechaExpiracion());

        Descuento actualizado = descuentoRepository.save(descuento);
        log.info("Descuento con ID: {} actualizado correctamente", actualizado.getId());
        return actualizado;
    }

    public void delete(Integer id) {
        log.warn("Eliminando descuento con ID: {}", id);
        descuentoRepository.deleteById(id);
        log.info("Descuento con ID: {} eliminado correctamente", id);
    }

    public boolean existsById(Integer id) {
        return descuentoRepository.existsById(id);
    }
}