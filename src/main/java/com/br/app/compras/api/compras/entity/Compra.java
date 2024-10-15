package com.br.app.compras.api.compras.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String nomeProduto;

    @Column(nullable = false)
    @NotNull
    private Integer quantidade;

    @Column(nullable = false)
    @NotNull
    private String cpfComprador;

    @Column(nullable = false)
    @NotNull
    private Double valorUnitario;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate dataHoraCompra;

}

