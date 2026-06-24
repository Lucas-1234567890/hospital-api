package com.hospital.api.controller;

import com.hospital.api.entity.Medico;
import com.hospital.api.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // POST /api/medicos - Cadastrar médico
    @PostMapping
    public ResponseEntity<Medico> cadastrar(@Valid @RequestBody Medico medico) {
        Medico salvo = medicoService.cadastrar(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /api/medicos - Listar todos
    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    // Exercício 9 - GET /api/medicos/ranking - Médicos por número de consultas
    @GetMapping("/ranking")
    public ResponseEntity<List<Medico>> rankingPorConsultas() {
        return ResponseEntity.ok(medicoService.listarPorNumeroDeConsultas());
    }
}
