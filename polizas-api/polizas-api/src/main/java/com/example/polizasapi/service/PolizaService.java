package com.example.polizasapi.service;

import com.example.polizasapi.entity.Poliza;
import com.example.polizasapi.entity.Riesgo;
import com.example.polizasapi.exception.BusinessException;
import com.example.polizasapi.repository.PolizaRepository;
import com.example.polizasapi.repository.RiesgoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolizaService {

    private static final BigDecimal IPC = new BigDecimal("0.097"); // 9.7% IPC ejemplo
    private static final String ESTADO_CANCELADA = "CANCELADA";
    private static final String ESTADO_RENOVADA  = "RENOVADA";
    private static final String ESTADO_ACTIVA    = "ACTIVA";
    private static final String TIPO_COLECTIVA   = "COLECTIVA";
    private static final String TIPO_INDIVIDUAL  = "INDIVIDUAL";

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;

    public List<Poliza> listar(String tipo, String estado) {
        if (tipo != null && estado != null) return polizaRepository.findByTipoAndEstado(tipo, estado);
        if (tipo != null)                  return polizaRepository.findByTipo(tipo);
        if (estado != null)                return polizaRepository.findByEstado(estado);
        return polizaRepository.findAll();
    }

    public List<Riesgo> listarRiesgos(Long polizaId) {
        obtenerPolizaOFallar(polizaId);
        return riesgoRepository.findByPolizaId(polizaId);
    }

    @Transactional
    public Poliza renovar(Long polizaId) {
        Poliza poliza = obtenerPolizaOFallar(polizaId);

        if (ESTADO_CANCELADA.equals(poliza.getEstado())) {
            throw new BusinessException("No se puede renovar una póliza cancelada.");
        }

        BigDecimal nuevoCanon = poliza.getCanon()
                .multiply(BigDecimal.ONE.add(IPC))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal nuevaPrima = poliza.getPrima()
                .multiply(BigDecimal.ONE.add(IPC))
                .setScale(2, RoundingMode.HALF_UP);

        poliza.setCanon(nuevoCanon);
        poliza.setPrima(nuevaPrima);
        poliza.setEstado(ESTADO_RENOVADA);

        log.info("Póliza {} renovada. Nuevo canon: {}, Nueva prima: {}", polizaId, nuevoCanon, nuevaPrima);
        return polizaRepository.save(poliza);
    }

    @Transactional
    public Poliza cancelar(Long polizaId) {
        Poliza poliza = obtenerPolizaOFallar(polizaId);

        if (ESTADO_CANCELADA.equals(poliza.getEstado())) {
            throw new BusinessException("La póliza ya está cancelada.");
        }

        // Cancela todos los riesgos asociados
        List<Riesgo> riesgos = riesgoRepository.findByPolizaId(polizaId);
        riesgos.forEach(r -> r.setEstado("CANCELADO"));
        riesgoRepository.saveAll(riesgos);

        poliza.setEstado(ESTADO_CANCELADA);
        log.info("Póliza {} cancelada junto con {} riesgos.", polizaId, riesgos.size());
        return polizaRepository.save(poliza);
    }

    @Transactional
    public Riesgo agregarRiesgo(Long polizaId, Riesgo riesgo) {
        Poliza poliza = obtenerPolizaOFallar(polizaId);

        if (!TIPO_COLECTIVA.equals(poliza.getTipo())) {
            throw new BusinessException("Solo se pueden agregar riesgos a pólizas de tipo COLECTIVA.");
        }

        if (ESTADO_CANCELADA.equals(poliza.getEstado())) {
            throw new BusinessException("No se pueden agregar riesgos a una póliza cancelada.");
        }

        if (TIPO_INDIVIDUAL.equals(poliza.getTipo())) {
            long activos = riesgoRepository.countByPolizaIdAndEstado(polizaId, "ACTIVO");
            if (activos >= 1) {
                throw new BusinessException("Una póliza individual solo puede tener 1 riesgo.");
            }
        }

        riesgo.setPoliza(poliza);
        riesgo.setEstado("ACTIVO");
        log.info("Riesgo agregado a póliza COLECTIVA {}", polizaId);
        return riesgoRepository.save(riesgo);
    }

    private Poliza obtenerPolizaOFallar(Long id) {
        return polizaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Póliza no encontrada con id: " + id));
    }
}
