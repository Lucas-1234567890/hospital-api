package com.hospital.api.service;

import com.hospital.api.entity.Medico;
import com.hospital.api.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico cadastrar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    // Exercício 9 - Médicos com mais consultas
    public List<Medico> listarPorNumeroDeConsultas() {
        return medicoRepository.findMedicosOrderByNumeroConsultas();
    }
}
