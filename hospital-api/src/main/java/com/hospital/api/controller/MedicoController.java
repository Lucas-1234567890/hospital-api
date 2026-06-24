package com.hospital.api.controller;

import com.hospital.api.entity.Medico;
import com.hospital.api.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Operações relacionadas ao gerenciamento de médicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Operation(summary = "Cadastrar médico", description = "Cadastra um novo médico no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Medico> cadastrar(@Valid @RequestBody Medico medico) {
        Medico salvo = medicoService.cadastrar(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @Operation(summary = "Listar médicos", description = "Retorna a lista de todos os médicos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @Operation(summary = "Ranking de médicos por consultas",
               description = "Retorna os médicos ordenados pelo número de consultas realizadas (maior para menor)")
    @ApiResponse(responseCode = "200", description = "Ranking retornado com sucesso")
    @GetMapping("/ranking")
    public ResponseEntity<List<Medico>> rankingPorConsultas() {
        return ResponseEntity.ok(medicoService.listarPorNumeroDeConsultas());
    }
}
