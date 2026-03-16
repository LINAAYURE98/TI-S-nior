package com.example.polizasapi.controller;

import com.example.polizasapi.entity.Riesgo;
import com.example.polizasapi.service.RiesgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riesgos")
@RequiredArgsConstructor
public class RiesgoController {

    private final RiesgoService riesgoService;

    // POST /riesgos/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Riesgo> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(riesgoService.cancelar(id));
    }
}
