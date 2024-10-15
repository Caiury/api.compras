package com.br.app.compras.api.compras.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Long id;

    @NotBlank(message = "O nome do produto não pode estar vazio.")
    private String nomeProduto;

    @NotNull(message = "A quantidade não pode ser nula.")
    private Integer quantidade;

    @NotBlank(message = "O CPF do comprador não pode estar vazio.")
    private String cpfComprador;

    @NotNull(message = "O valor unitário não pode ser nulo.")
    private Double valorUnitario;

    public String getCpfComprador() {
        return formatarCpf(cpfComprador);
    }

    private String formatarCpf(String cpf) {
        if (cpf == null) return null;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() == 11) {
            return "**" + cpf.substring(2, 4) + "." + cpf.substring(4, 5) + "**." +
                    cpf.substring(5, 8) + "-" + cpf.substring(8);
        }

        return cpf;
    }

}