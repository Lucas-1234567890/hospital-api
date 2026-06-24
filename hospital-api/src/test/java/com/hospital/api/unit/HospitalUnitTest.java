package com.hospital.api.unit;

import com.hospital.api.entity.Medico;
import com.hospital.api.entity.Paciente;
import com.hospital.api.repository.MedicoRepository;
import com.hospital.api.repository.PacienteRepository;
import com.hospital.api.service.MedicoService;
import com.hospital.api.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalUnitTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @InjectMocks
    private MedicoService medicoService;

    private Paciente paciente;
    private Medico medico;

    @BeforeEach
    void setUp() {
        paciente = new Paciente("João Silva", "12345678901", LocalDate.of(1985, 3, 15), "71999990001");
        paciente.setId(1L);

        medico = new Medico("Dr. Carlos", "CRM-BA-12345", "Cardiologista");
        medico.setId(1L);
    }

    // --- TESTES DE PACIENTE ---

    @Test
    @DisplayName("Deve cadastrar paciente com sucesso")
    void deveCadastrarPacienteComSucesso() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = pacienteService.cadastrar(paciente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("João Silva");
        assertThat(resultado.getCpf()).isEqualTo("12345678901");
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve buscar paciente por ID com sucesso")
    void deveBuscarPacientePorId() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        assertThat(resultado.get().getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Deve retornar vazio quando paciente não existe")
    void deveRetornarVazioQuandoPacienteNaoExiste() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Paciente> resultado = pacienteService.buscarPorId(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover paciente inexistente")
    void deveLancarExcecaoAoRemoverPacienteInexistente() {
        when(pacienteRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> pacienteService.remover(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paciente não encontrado com id: 99");

        verify(pacienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve excluir paciente com sucesso")
    void deveExcluirPacienteComSucesso() {
        when(pacienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pacienteRepository).deleteById(1L);

        assertThatNoException().isThrownBy(() -> pacienteService.remover(1L));
        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    // --- TESTES DE MÉDICO ---

    @Test
    @DisplayName("Deve cadastrar médico com sucesso")
    void deveCadastrarMedicoComSucesso() {
        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);

        Medico resultado = medicoService.cadastrar(medico);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Dr. Carlos");
        assertThat(resultado.getEspecialidade()).isEqualTo("Cardiologista");
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    @DisplayName("Deve listar todos os médicos")
    void deveListarTodosMedicos() {
        when(medicoRepository.findAll()).thenReturn(List.of(medico));

        List<Medico> resultado = medicoService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCrm()).isEqualTo("CRM-BA-12345");
    }
}
