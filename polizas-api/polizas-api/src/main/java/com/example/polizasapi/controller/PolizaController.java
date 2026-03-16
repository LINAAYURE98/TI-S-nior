package com.example.polizasapi.controller;

import com.example.polizasapi.entity.Poliza;
import com.example.polizasapi.entity.Riesgo;
import com.example.polizasapi.service.PolizaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService polizaService;

    // GET /polizas?tipo=INDIVIDUAL&estado=ACTIVA
    @GetMapping
    public ResponseEntity<List<Poliza>> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(polizaService.listar(tipo, estado));
    }

    // GET /polizas/{id}/riesgos
    @GetMapping("/{id}/riesgos")
    public ResponseEntity<List<Riesgo>> listarRiesgos(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.listarRiesgos(id));
    }

    // POST /polizas/{id}/renovar
    @PostMapping("/{id}/renovar")
    public ResponseEntity<Poliza> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.renovar(id));
    }

    // POST /polizas/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Poliza> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.cancelar(id));
    }

    // POST /polizas/{id}/riesgos
    @PostMapping("/{id}/riesgos")
    public ResponseEntity<Riesgo> agregarRiesgo(
            @PathVariable Long id,
            @RequestBody Riesgo riesgo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(polizaService.agregarRiesgo(id, riesgo));
    }
}
