package com.hospital.api.repository;

import com.hospital.api.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Exercício 9 - Query method: médicos com mais consultas ordenados desc
    @Query("SELECT m FROM Medico m LEFT JOIN m.consultas c GROUP BY m ORDER BY COUNT(c) DESC")
    List<Medico> findMedicosOrderByNumeroConsultas();
}
