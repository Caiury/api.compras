package com.br.app.compras.api.compras.service;

import com.br.app.compras.api.compras.DTO.CompraDTO;
import com.br.app.compras.api.compras.entity.Compra;
import com.br.app.compras.api.compras.exception.CpfInvalidoExceptions;
import com.br.app.compras.api.compras.exception.QuantidadeInvalidaException;
import com.br.app.compras.api.compras.repository.CompraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompraDTO cadastrarCompra(Compra compra) {
        List<Compra> comprasExistentes = compraRepository.findByCpfComprador(compra.getCpfComprador());

        long totalComprasProduto = comprasExistentes.stream()
                .filter(c -> c.getId().equals(compra.getId()))
                .mapToInt(Compra::getQuantidade)
                .sum();

        if (totalComprasProduto + compra.getQuantidade() > 3) {
            throw new QuantidadeInvalidaException("Limite de compras atingido para este produto.");
        }
        compra.setCpfComprador(formatarCpf(compra.getCpfComprador()));

        Compra novaCompra = compraRepository.save(compra);
        return modelMapper.map(novaCompra, CompraDTO.class);
    }

    public List<CompraDTO> pesquisarCompras(String cpfComprador, String nomeProduto, LocalDate dataInicio, LocalDate dataFim) {
        List<Compra> compras = compraRepository.findByCpfCompradorAndNomeProdutoContainingIgnoreCaseAndDataHoraCompraBetween(cpfComprador, nomeProduto, dataInicio, dataFim);
        return compras.stream()
                .map(compra -> modelMapper.map(compra, CompraDTO.class))
                .collect(Collectors.toList());
    }

    public List<CompraDTO> relatorioCompras(LocalDate dataInicio, LocalDate dataFim) {
        List<Compra> compras = compraRepository.findByDataHoraCompraBetween(dataInicio, dataFim);

        return compras.stream()
                .map(compra -> modelMapper.map(compra, CompraDTO.class))
                .collect(Collectors.toList());

    }

    private String formatarCpf(String cpf) {
        if (cpf == null) return null;

        if (cpf.length() == 11) {
            return cpf;
        }

        throw new CpfInvalidoExceptions("CPF deve ter 11 dígitos.");
    }

}
