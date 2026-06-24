package com.hospital.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "internacoes")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data de entrada é obrigatória")
    @Column(nullable = false)
    private LocalDate dataEntrada;

    private LocalDate dataAlta;

    @Column(length = 10)
    private String quarto;

    // Exercício 6 - Um Paciente pode ter várias Internações
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Internacao() {}

    public Internacao(LocalDate dataEntrada, LocalDate dataAlta, String quarto, Paciente paciente) {
        this.dataEntrada = dataEntrada;
        this.dataAlta = dataAlta;
        this.quarto = quarto;
        this.paciente = paciente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalDate getDataAlta() { return dataAlta; }
    public void setDataAlta(LocalDate dataAlta) { this.dataAlta = dataAlta; }
    public String getQuarto() { return quarto; }
    public void setQuarto(String quarto) { this.quarto = quarto; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}
