package com.example.polizasapi.repository;

import com.example.polizasapi.entity.Riesgo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {
    List<Riesgo> findByPolizaId(Long polizaId);
    long countByPolizaIdAndEstado(Long polizaId, String estado);
}
