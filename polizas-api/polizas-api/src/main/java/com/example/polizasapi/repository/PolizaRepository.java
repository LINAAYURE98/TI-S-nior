package com.example.polizasapi.repository;

import com.example.polizasapi.entity.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {
    List<Poliza> findByTipo(String tipo);
    List<Poliza> findByEstado(String estado);
    List<Poliza> findByTipoAndEstado(String tipo, String estado);
}
