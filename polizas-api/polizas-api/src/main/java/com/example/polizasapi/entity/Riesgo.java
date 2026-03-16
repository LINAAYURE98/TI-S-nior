package com.example.polizasapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "riesgos")
public class Riesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String estado; // ACTIVO, CANCELADO

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String arrendatario;

    @ManyToOne
    @JoinColumn(name = "poliza_id", nullable = false)
    private Poliza poliza;
}
