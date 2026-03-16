package com.example.polizasapi.service;

import com.example.polizasapi.entity.Riesgo;
import com.example.polizasapi.exception.BusinessException;
import com.example.polizasapi.repository.RiesgoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiesgoService {

    private final RiesgoRepository riesgoRepository;

    @Transactional
    public Riesgo cancelar(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new EntityNotFoundException("Riesgo no encontrado con id: " + riesgoId));

        if ("CANCELADO".equals(riesgo.getEstado())) {
            throw new BusinessException("El riesgo ya está cancelado.");
        }

        riesgo.setEstado("CANCELADO");
        log.info("Riesgo {} cancelado.", riesgoId);
        return riesgoRepository.save(riesgo);
    }
}
