package com.crash.br.adapter.broker.in.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnderecoRequest(
                       String rua,
                       String bairro,
                       String cidade,
                       String estado,
                       String cep,
                       @JsonProperty("numero_da_casa") Integer numeroDaCasa,
                       Integer telefone) {
}
