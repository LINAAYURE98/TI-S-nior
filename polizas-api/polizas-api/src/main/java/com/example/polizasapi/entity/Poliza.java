package com.example.polizasapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "polizas")
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // INDIVIDUAL o COLECTIVA

    @Column(nullable = false)
    private String estado; // ACTIVA, RENOVADA, CANCELADA

    @Column(nullable = false)
    private BigDecimal canon;

    @Column(nullable = false)
    private BigDecimal prima;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String tomador;

    @Column(nullable = false)
    private String beneficiario;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL)
    private List<Riesgo> riesgos;
}
