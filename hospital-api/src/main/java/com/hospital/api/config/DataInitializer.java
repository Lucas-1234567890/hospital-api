package com.hospital.api.config;

import com.hospital.api.entity.Medico;
import com.hospital.api.entity.Paciente;
import com.hospital.api.repository.MedicoRepository;
import com.hospital.api.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        return args -> {
            // Inserir médicos apenas se ainda não existirem
            if (medicoRepository.count() == 0) {
                medicoRepository.save(new Medico("Dr. Carlos Andrade", "CRM-BA-12345", "Cardiologista"));
                medicoRepository.save(new Medico("Dra. Fernanda Lima", "CRM-BA-67890", "Ortopedista"));
                System.out.println(">>> Médicos iniciais inseridos.");
            }

            // Inserir pacientes apenas se ainda não existirem
            if (pacienteRepository.count() == 0) {
                pacienteRepository.save(new Paciente("João Silva", "12345678901", LocalDate.of(1985, 3, 15), "71999990001"));
                pacienteRepository.save(new Paciente("Maria Oliveira", "98765432100", LocalDate.of(1992, 7, 22), "71999990002"));
                System.out.println(">>> Pacientes iniciais inseridos.");
            }
        };
    }
}
