package com.hospital.api.controller;

import com.hospital.api.entity.Paciente;
import com.hospital.api.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operações relacionadas ao gerenciamento de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Cadastrar paciente", description = "Cadastra um novo paciente no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@Valid @RequestBody Paciente paciente) {
        Paciente salvo = pacienteService.cadastrar(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente pelo seu identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(
            @Parameter(description = "ID do paciente", required = true) @PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar pacientes", description = "Retorna a lista de todos os pacientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @Operation(summary = "Remover paciente", description = "Remove um paciente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Paciente removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID do paciente", required = true) @PathVariable Long id) {
        try {
            pacienteService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
