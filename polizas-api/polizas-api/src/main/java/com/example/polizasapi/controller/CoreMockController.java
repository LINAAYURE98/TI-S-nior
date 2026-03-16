package com.example.polizasapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    // POST /core-mock/evento
    @PostMapping("/evento")
    public ResponseEntity<Map<String, String>> recibirEvento(@RequestBody Map<String, Object> body) {
        String evento   = String.valueOf(body.get("evento"));
        Object polizaId = body.get("polizaId");

        log.info(">>> CORE MOCK: Evento '{}' para polizaId '{}' intentado enviar al CORE.", evento, polizaId);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Evento registrado en logs correctamente.",
                "evento", evento,
                "polizaId", String.valueOf(polizaId)
        ));
    }
}
