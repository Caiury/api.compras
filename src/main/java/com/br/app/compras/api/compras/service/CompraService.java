package com.br.app.compras.api.compras.service;

import com.br.app.compras.api.compras.entity.Compra;
import com.br.app.compras.api.compras.exception.CpfInvalidoExceptions;
import com.br.app.compras.api.compras.exception.QuantidadeInvalidaException;
import com.br.app.compras.api.compras.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public Compra cadastrarCompra(Compra compra) {
        List<Compra> comprasExistentes = compraRepository.findByCpfComprador(compra.getCpfComprador());

        String cpfSemMascara = compra.getCpfComprador().replaceAll("\\D", "");
        long totalComprasProduto = comprasExistentes.stream()
                .filter(c -> c.getId().equals(compra.getId()))
                .mapToInt(Compra::getQuantidade)
                .sum();

        if (totalComprasProduto + compra.getQuantidade() > 3) {
            throw new QuantidadeInvalidaException("Limite de compras atingido para este produto.");
        }

        compra.setCpfComprador(formatarCpf(cpfSemMascara));
        return compraRepository.save(compra);
    }

    public List<Compra> pesquisarCompras(String cpfComprador, String nomeProduto, LocalDate dataInicio, LocalDate dataFim) {
        return compraRepository.findByCpfCompradorAndNomeProdutoContainingIgnoreCaseAndDataHoraCompraBetween(cpfComprador, nomeProduto, dataInicio, dataFim);
    }

    public List<Compra> relatorioCompras(LocalDate dataInicio, LocalDate dataFim) {
        return compraRepository.findByDataHoraCompraBetween(dataInicio, dataFim);
    }

    private String formatarCpf(String cpf) {
        if (cpf == null) return null;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() == 11) {
            return "**" + cpf.substring(2, 4) + "." + cpf.substring(4, 5) + "**." +
                    cpf.substring(5, 8) + "-" + cpf.substring(8);
        }

        throw new CpfInvalidoExceptions("CPF deve ter 11 dígitos.");
    }
}
