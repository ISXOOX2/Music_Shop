package org.example.descuento.service;

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
        try {
            log.info("Obteniendo lista de todos los descuentos");
            return descuentoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al obtener la lista de descuentos: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de descuentos");
        }
    }

    public Optional<Descuento> findById(Integer id) {
        try {
            log.info("Buscando descuento con ID: {}", id);
            Optional<Descuento> resultado = descuentoRepository.findById(id);
            if (resultado.isEmpty()) {
                log.warn("No se encontró ningún descuento con ID: {}", id);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar descuento con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el descuento con ID: " + id);
        }
    }

    public Descuento findByCodigo(String codigo) {
        try {
            log.info("Buscando descuento con código: {}", codigo);
            Descuento resultado = descuentoRepository.findByCodigo(codigo);
            if (resultado == null) {
                log.warn("No se encontró ningún descuento con código: {}", codigo);
            }
            return resultado;
        } catch (Exception e) {
            log.error("Error al buscar descuento con código {}: {}", codigo, e.getMessage());
            throw new RuntimeException("Error al buscar el descuento con código: " + codigo);
        }
    }

    public List<Descuento> findByPorcentaje(Double porcentaje) {
        try {
            log.info("Buscando descuentos con porcentaje: {}", porcentaje);
            return descuentoRepository.findByPorcentaje(porcentaje);
        } catch (Exception e) {
            log.error("Error al buscar descuentos con porcentaje {}: {}", porcentaje, e.getMessage());
            throw new RuntimeException("Error al buscar descuentos con porcentaje: " + porcentaje);
        }
    }

    public Descuento save(Descuento descuento) {
        try {
            log.info("Guardando descuento con código: {}", descuento.getCodigo());
            Descuento guardado = descuentoRepository.save(descuento);
            log.info("Descuento guardado exitosamente con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error al guardar descuento con código '{}': {}", descuento.getCodigo(), e.getMessage());
            throw new RuntimeException("No se pudo guardar el descuento con código: " + descuento.getCodigo());
        }
    }

    public void delete(Integer id) {
        try {
            log.warn("Eliminando descuento con ID: {}", id);
            descuentoRepository.deleteById(id);
            log.info("Descuento con ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar descuento con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("No se pudo eliminar el descuento con ID: " + id);
        }
    }

    public boolean existsById(Integer id) {
        return descuentoRepository.existsById(id);
    }
}