package com.br.app.compras.api.compras.repository;

import com.br.app.compras.api.compras.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByCpfComprador(String cpfComprador);

    List<Compra> findByDataHoraCompraBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Compra> findByCpfCompradorAndNomeProdutoContainingIgnoreCaseAndDataHoraCompraBetween(
            String cpfComprador,
            String nomeProduto,
            LocalDate dataInicio,
            LocalDate dataFim
    );
}