package com.hospital.api.service;

import com.hospital.api.entity.Consulta;
import com.hospital.api.entity.Medico;
import com.hospital.api.entity.Paciente;
import com.hospital.api.repository.ConsultaRepository;
import com.hospital.api.repository.MedicoRepository;
import com.hospital.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Consulta cadastrar(Consulta consulta, Long pacienteId, Long medicoId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com id: " + pacienteId));
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com id: " + medicoId));
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        return consultaRepository.save(consulta);
    }
}
