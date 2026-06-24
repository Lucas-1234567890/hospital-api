package com.hospital.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.api.entity.Paciente;
import com.hospital.api.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PacienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        pacienteRepository.deleteAll();
        paciente = new Paciente("Ana Paula", "11122233344", LocalDate.of(1990, 5, 10), "71988880000");
    }

    // Teste 1 - Cadastrar paciente via API
    @Test
    @DisplayName("Teste 1: Deve cadastrar paciente via API e retornar 201")
    void deveCadastrarPaciente() throws Exception {
        mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Ana Paula"))
                .andExpect(jsonPath("$.cpf").value("11122233344"));
    }

    // Teste 2 - Buscar paciente cadastrado
    @Test
    @DisplayName("Teste 2: Deve buscar paciente cadastrado por ID")
    void deveBuscarPacienteCadastrado() throws Exception {
        Paciente salvo = pacienteRepository.save(paciente);

        mockMvc.perform(get("/api/pacientes/{id}", salvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salvo.getId()))
                .andExpect(jsonPath("$.nome").value("Ana Paula"))
                .andExpect(jsonPath("$.telefone").value("71988880000"));
    }

    // Teste 3 - Listar todos os pacientes
    @Test
    @DisplayName("Teste 3: Deve listar todos os pacientes")
    void deveListarTodosPacientes() throws Exception {
        pacienteRepository.save(paciente);
        pacienteRepository.save(new Paciente("Carlos Souza", "55566677788", LocalDate.of(1975, 1, 20), "71977770000"));

        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[*].nome", hasItem("Ana Paula")))
                .andExpect(jsonPath("$[*].nome", hasItem("Carlos Souza")));
    }

    // Teste 4 - Excluir paciente
    @Test
    @DisplayName("Teste 4: Deve excluir paciente e retornar 204")
    void deveExcluirPaciente() throws Exception {
        Paciente salvo = pacienteRepository.save(paciente);

        mockMvc.perform(delete("/api/pacientes/{id}", salvo.getId()))
                .andExpect(status().isNoContent());

        // Valida que o paciente não existe mais
        mockMvc.perform(get("/api/pacientes/{id}", salvo.getId()))
                .andExpect(status().isNotFound());
    }

    // Teste extra - Buscar paciente inexistente
    @Test
    @DisplayName("Deve retornar 404 quando paciente não existe")
    void deveRetornar404PacienteInexistente() throws Exception {
        mockMvc.perform(get("/api/pacientes/9999"))
                .andExpect(status().isNotFound());
    }
}
