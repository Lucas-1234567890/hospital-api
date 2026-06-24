package com.hospital.api.controller;

import com.hospital.api.entity.Consulta;
import com.hospital.api.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Operações relacionadas ao agendamento de consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @Operation(summary = "Cadastrar consulta",
               description = "Cadastra uma nova consulta vinculando um paciente e um médico existentes")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Consulta cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Paciente ou médico não encontrado")
    })
    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @Valid @RequestBody Consulta consulta,
            @Parameter(description = "ID do paciente", required = true) @RequestParam Long pacienteId,
            @Parameter(description = "ID do médico", required = true) @RequestParam Long medicoId) {
        Consulta salva = consultaService.cadastrar(consulta, pacienteId, medicoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }
}
