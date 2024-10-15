package com.br.app.compras.api.compras.controller;

import com.br.app.compras.api.compras.entity.Compra;
import com.br.app.compras.api.compras.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> cadastrarCompra(@RequestBody @Valid Compra compra) {
        try {
            Compra novaCompra = compraService.cadastrarCompra(compra);
            return ResponseEntity.status(201).body(novaCompra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Compra>> pesquisarCompras(
            @RequestParam(required = true) String cpfComprador,
            @RequestParam(required = true) String nomeProduto,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<Compra> compras = compraService.pesquisarCompras(cpfComprador, nomeProduto, dataInicio, dataFim);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<Compra>> relatorioCompras(
            @RequestParam String dataInicio,
            @RequestParam String dataFim) {

        dataInicio = dataInicio.trim();
        dataFim = dataFim.trim();

        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);

        List<Compra> relatorio = compraService.relatorioCompras(inicio, fim);
        return ResponseEntity.ok(relatorio);
    }
}
