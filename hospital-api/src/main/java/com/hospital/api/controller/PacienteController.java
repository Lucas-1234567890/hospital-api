package com.hospital.api.controller;

import com.hospital.api.entity.Paciente;
import com.hospital.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // POST /api/pacientes - Cadastrar paciente
    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@Valid @RequestBody Paciente paciente) {
        Paciente salvo = pacienteService.cadastrar(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /api/pacientes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/pacientes - Listar todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    // DELETE /api/pacientes/{id} - Remover paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            pacienteService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
